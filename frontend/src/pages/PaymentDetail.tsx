import { useNavigate, useParams } from 'react-router-dom';
import '../App.css';
import { Booking } from '../types';
import { formatRand } from '../data/services';

interface PaymentDetailProps {
  bookings: Booking[];
  businessName: string;
}

// Payment status is derived from the booking: fees are paid on the day,
// so only a completed session counts as paid.
function paymentStatus(b: Booking): { label: string; note: string } {
  switch (b.status) {
    case 'Completed':
      return { label: 'Paid', note: 'Payment received at session' };
    case 'Cancelled':
      return { label: 'Not payable', note: 'Booking cancelled — nothing is owed' };
    case 'Confirmed':
      return { label: 'Payable at session', note: 'Booking confirmed — pay by EFT or cash on the day' };
    default:
      return { label: 'Awaiting confirmation', note: 'Nothing is due until Traabco confirms your booking' };
  }
}

function PaymentDetail({ bookings, businessName }: PaymentDetailProps) {
  const navigate = useNavigate();
  const { bookingId } = useParams<{ bookingId: string }>();
  const booking = bookings.find((b) => b.reference === bookingId);

  if (!booking) {
    return (
      <div className="page center">
        <div className="error-icon">?</div>
        <h1>Booking not found</h1>
        <p className="subtitle">
          We couldn't find a booking with reference <strong>{bookingId}</strong>.
          It may have been cleared when the page was refreshed.
        </p>
        <div className="button-row">
          <button type="button" className="btn-primary" onClick={() => navigate('/dashboard')}>
            Back to my account
          </button>
        </div>
      </div>
    );
  }

  const status = paymentStatus(booking);

  return (
    <div className="pd-page">
      <div className="pd-frame">
        <div className="pd-screen-label">Payment Detail</div>

        {/* Header */}
        <header className="pd-header">
          <div className="pd-brand">TRAABCO</div>
          <div className="pd-header-divider">|</div>
          <div className="pd-header-sub">Payment details</div>
          <button className="pd-back" onClick={() => navigate('/dashboard')}>
            ← Back to account
          </button>
        </header>

        {/* Title Row */}
        <div className="pd-title-row">
          <div>
            <h1 className="pd-title">Transaction Details · {booking.reference}</h1>
            <p className="pd-subtitle">
              {booking.service} · {booking.date} · {booking.time}
            </p>
          </div>
          <div className="pd-title-actions">
            <button className="pd-text-btn" onClick={() => window.print()}>Print</button>
            <span className="pd-pill pd-pill-lg">{status.label}</span>
          </div>
        </div>

        {/* Info Grid Layout */}
        <div className="pd-grid">
          {/* Column 1 Content */}
          <div className="pd-c1 pd-r1">
            <div className="pd-label">Consulting Session Fee</div>
            <div className="pd-amount">{formatRand(booking.fee)}</div>
          </div>

          <div className="pd-c1 pd-r2">
            <div className="pd-label">Business Name</div>
            <div className="pd-value">{businessName}</div>
          </div>

          <div className="pd-c1 pd-r3">
            <div className="pd-label">Consultant</div>
            <div className="pd-value">{booking.consultant}</div>
          </div>

          {/* Column 2 Content */}
          <div className="pd-c2 pd-r1">
            <div className="pd-label">Payment Method</div>
            <div className="pd-value">EFT or Cash on day of session</div>
          </div>

          <div className="pd-c2 pd-r2">
            <div className="pd-label">Location Venue</div>
            <div className="pd-value">{booking.location}</div>
          </div>

          {/* Side Receipt Column */}
          <aside className="pd-side">
            <div className="pd-receipt-file">
              <strong>Payment Status</strong>
              <br />
              {status.note}
            </div>
            <button
              className="pd-btn-primary"
              onClick={() => navigate('/book')}
            >
              Book Another Service →
            </button>
          </aside>

          {/* Guidelines Tip Section */}
          <section className="pd-tip">
            <div className="pd-tip-title">Important Guidelines:</div>
            <p className="pd-tip-text">
              Traabco will review availability and confirm your booking request
              within <strong>24 hours</strong> of submission.
            </p>
          </section>

          {/* Reference Link Row */}
          <div className="pd-linked">
            <div className="pd-linked-row">
              <div>
                <div className="pd-linked-title">Need a different date or service?</div>
                <div className="pd-linked-meta">Make a new booking request and Traabco will update your schedule.</div>
              </div>
              <button className="pd-view" onClick={() => navigate('/book', { state: { autoSelectId: booking.serviceId } })}>
                New request
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default PaymentDetail;
