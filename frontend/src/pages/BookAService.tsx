import { useMemo, useState, Dispatch, SetStateAction } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import ConfirmBookingModal from '../component/ConfirmBookingModal';
import { Service, BookingDraft, Booking } from '../types';
import services, { findService, formatRand } from '../data/services';

const monthNames = [
  'January', 'February', 'March', 'April', 'May', 'June',
  'July', 'August', 'September', 'October', 'November', 'December',
];
const quickTimes = ['09:00', '10:00', '13:00', '14:00', '15:00'];

// Builds a reference that won't repeat within a session, e.g. BK-2026-483920.
function makeReference(): string {
  const year = new Date().getFullYear();
  const stamp = Date.now().toString().slice(-6);
  return `BK-${year}-${stamp}`;
}

// TODO: replace with a real API call once the backend exists.
// Deliberately "fails" ~30% of the time so both the success and failed
// screens are reachable during testing — remove the randomness once wired up.
function fakeSubmitBooking(): Promise<{ ok: boolean; reference?: string; reason?: string }> {
  return new Promise((resolve) => {
    setTimeout(() => {
      if (Math.random() < 0.3) {
        resolve({ ok: false, reason: 'The selected slot was just taken by another client.' });
      } else {
        resolve({ ok: true, reference: makeReference() });
      }
    }, 800);
  });
}

function addMinutes(time: string, minutes: number): string {
  const [h, m] = time.split(':').map(Number);
  const total = h * 60 + m + minutes;
  const hh = Math.floor(total / 60) % 24;
  const mm = total % 60;
  return `${String(hh).padStart(2, '0')}:${String(mm).padStart(2, '0')}`;
}

// Builds a Monday-first grid of every day in the given month, with
// leading blank cells so the 1st lands under the correct weekday.
function buildCalendarCells(year: number, monthIndex: number): (Date | null)[] {
  const firstOfMonth = new Date(year, monthIndex, 1);
  const daysInMonth = new Date(year, monthIndex + 1, 0).getDate();
  const mondayFirstOffset = (firstOfMonth.getDay() + 6) % 7; // 0 = Monday

  const cells: (Date | null)[] = [];
  for (let i = 0; i < mondayFirstOffset; i++) cells.push(null);
  for (let d = 1; d <= daysInMonth; d++) cells.push(new Date(year, monthIndex, d));
  return cells;
}

function isSameDay(a: Date, b: Date) {
  return a.getFullYear() === b.getFullYear() && a.getMonth() === b.getMonth() && a.getDate() === b.getDate();
}

interface BookAServiceProps {
  setBookings: Dispatch<SetStateAction<Booking[]>>;
}

export default function BookAService({ setBookings }: BookAServiceProps) {
  const navigate = useNavigate();
  const location = useLocation();

  // "Book Now" on the Services page passes the chosen service id in navigation state.
  const autoSelectId = (location.state as { autoSelectId?: string } | null)?.autoSelectId;

  const today = useMemo(() => {
    const t = new Date();
    t.setHours(0, 0, 0, 0);
    return t;
  }, []);

  const [selectedService, setSelectedService] = useState<Service | null>(
    findService(autoSelectId) ?? services[0]
  );
  // Open the calendar on the current month, with no date picked yet,
  // so a past date can never be submitted by default.
  const [viewYear, setViewYear] = useState(today.getFullYear());
  const [viewMonth, setViewMonth] = useState(today.getMonth());
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);
  const [selectedTime, setSelectedTime] = useState<string>('10:00');
  const [modalOpen, setModalOpen] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const calendarCells = useMemo(() => buildCalendarCells(viewYear, viewMonth), [viewYear, viewMonth]);

  const viewingCurrentMonth = viewYear === today.getFullYear() && viewMonth === today.getMonth();

  const goToPrevMonth = () => {
    if (viewingCurrentMonth) return; // nothing bookable in past months
    setSelectedDate(null);
    if (viewMonth === 0) { setViewMonth(11); setViewYear((y) => y - 1); }
    else setViewMonth((m) => m - 1);
  };

  const goToNextMonth = () => {
    setSelectedDate(null);
    if (viewMonth === 11) { setViewMonth(0); setViewYear((y) => y + 1); }
    else setViewMonth((m) => m + 1);
  };

  const canConfirm = !!selectedService && !!selectedDate && !!selectedTime && selectedDate >= today;

  const draft: BookingDraft | null = canConfirm
    ? {
        service: selectedService as Service,
        date: (selectedDate as Date).toDateString(),
        time: `${selectedTime} – ${addMinutes(selectedTime, (selectedService as Service).durationMinutes)}`,
      }
    : null;

  const handleConfirm = async () => {
    if (!draft || !selectedService) return;
    setSubmitting(true);
    const result = await fakeSubmitBooking();
    setSubmitting(false);
    setModalOpen(false);

    if (result.ok) {
      const reference = result.reference || makeReference();
      const newBooking: Booking = {
        reference,
        serviceId: selectedService.id,
        service: selectedService.name,
        scope: `${selectedService.durationMinutes}-minute session`,
        date: draft.date,
        time: draft.time,
        location: '83 Madeira St, Mthatha',
        consultant: selectedService.consultant,
        fee: selectedService.fee,
        status: 'Pending',
      };

      setBookings((prev) => [newBooking, ...prev]);
      navigate('/book/success', { state: { ...draft, reference } });
    } else {
      navigate('/book/failed', { state: { reason: result.reason } });
    }
  };

  return (
    <div className="page">
      <h1>Book a service</h1>
      <p className="subtitle">Choose a service, pick a date and time, then confirm your request</p>

      <p className="step-title"><span className="step-num">1</span>Choose a service</p>
      <div className="service-grid">
        {services.map((service) => (
          <button
            key={service.id}
            type="button"
            className={`service-card ${selectedService?.id === service.id ? 'selected' : ''}`}
            onClick={() => setSelectedService(service)}
          >
            <p className="service-name">{service.name}</p>
            <p className="service-meta">
              {service.durationMinutes} min · {formatRand(service.fee)}
            </p>
            <p className="service-meta">{service.consultant}</p>
          </button>
        ))}
      </div>

      <p className="step-title"><span className="step-num">2</span>Pick a date and time</p>
      <div className="booking-columns">
        <div className="calendar-panel">
          <div className="calendar-nav">
            <button type="button" onClick={goToPrevMonth} disabled={viewingCurrentMonth}>← Prev</button>
            <p>{monthNames[viewMonth]} {viewYear}</p>
            <button type="button" onClick={goToNextMonth}>Next →</button>
          </div>
          <div className="calendar-weekdays">
            {['M', 'T', 'W', 'T', 'F', 'S', 'S'].map((d, i) => <span key={i}>{d}</span>)}
          </div>
          <div className="calendar-grid">
            {calendarCells.map((date, i) => {
              if (!date) return <div key={i} />;
              const isPast = date < today;
              const isSelected = selectedDate ? isSameDay(date, selectedDate) : false;
              return (
                <button
                  key={i}
                  type="button"
                  disabled={isPast}
                  className={`day ${isSelected ? 'selected' : ''} ${isPast ? 'past' : ''}`}
                  onClick={() => setSelectedDate(date)}
                >
                  {date.getDate()}
                </button>
              );
            })}
          </div>
        </div>

        <div className="slots-column">
          <p className="field-label">Pick a time</p>
          <input
            type="time"
            className="time-input"
            value={selectedTime}
            onChange={(e) => setSelectedTime(e.target.value)}
          />

          <p className="field-label" style={{ marginTop: 14 }}>Or choose a common slot</p>
          <div className="slot-grid">
            {quickTimes.map((time) => (
              <button
                key={time}
                type="button"
                className={`slot ${selectedTime === time ? 'selected' : ''}`}
                onClick={() => setSelectedTime(time)}
              >
                {time}
              </button>
            ))}
          </div>

          {draft ? (
            <div className="summary-card">
              <div className="summary-row"><span>Service</span><span>{draft.service.name}</span></div>
              <div className="summary-row"><span>Date</span><span>{draft.date}</span></div>
              <div className="summary-row"><span>Time</span><span>{draft.time}</span></div>
              <div className="summary-row"><span>Fee</span><span>{formatRand(draft.service.fee)}</span></div>
            </div>
          ) : (
            <p className="subtitle" style={{ marginTop: 14 }}>Select a date on the calendar to see your booking summary.</p>
          )}
        </div>
      </div>

      <div className="button-row" style={{ justifyContent: 'space-between' }}>
        <button type="button" onClick={() => navigate('/dashboard')}>← Back to my account</button>
        <button
          type="button"
          className="btn-primary"
          disabled={!canConfirm}
          onClick={() => setModalOpen(true)}
        >
          Confirm booking →
        </button>
      </div>

      {draft && (
        <ConfirmBookingModal
          isOpen={modalOpen}
          booking={draft}
          isSubmitting={submitting}
          onClose={() => setModalOpen(false)}
          onConfirm={handleConfirm}
        />
      )}
    </div>
  );
}
