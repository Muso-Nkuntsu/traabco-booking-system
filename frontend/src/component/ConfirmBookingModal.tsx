import { useEffect, useState } from 'react';
import { BookingDraft } from '../types';
import { formatRand } from '../data/services';

interface ConfirmBookingModalProps {
  isOpen: boolean;
  booking: BookingDraft;
  isSubmitting?: boolean;
  onClose: () => void;
  onConfirm: () => void;
}

export default function ConfirmBookingModal({
  isOpen,
  booking,
  isSubmitting = false,
  onClose,
  onConfirm,
}: ConfirmBookingModalProps) {
  const [agreed, setAgreed] = useState(false);

  // Untick the agreement each time the modal is closed, so it must be re-read.
  useEffect(() => {
    if (!isOpen) setAgreed(false);
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-card">
        <h2>Confirm your booking</h2>
        <p className="subtitle">Please check the details below before submitting</p>

        <div className="summary-card">
          <div className="summary-row"><span>Service</span><span>{booking.service.name}</span></div>
          <div className="summary-row"><span>Date &amp; time</span><span>{booking.date} · {booking.time}</span></div>
          <div className="summary-row"><span>Duration</span><span>{booking.service.durationMinutes} minutes</span></div>
          <div className="summary-row"><span>Consultant</span><span>{booking.service.consultant}</span></div>
          <div className="summary-row"><span>Fee</span><span>{formatRand(booking.service.fee)} · payable at session</span></div>
        </div>

        <label className="checkbox-row">
          <input
            type="checkbox"
            checked={agreed}
            onChange={(e) => setAgreed(e.target.checked)}
          />
          I understand this is a request. Traabco will confirm within 24 hours.
        </label>

        <div className="button-row">
          <button type="button" onClick={onClose} disabled={isSubmitting}>
            Back to edit
          </button>
          <button
            type="button"
            className="btn-primary"
            disabled={!agreed || isSubmitting}
            onClick={onConfirm}
          >
            {isSubmitting ? 'Submitting…' : 'Confirm booking'}
          </button>
        </div>
      </div>
    </div>
  );
}
