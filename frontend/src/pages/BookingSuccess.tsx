import { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { ConfirmedBooking } from '../types';
import { formatRand } from '../data/services';

export default function BookingSuccess() {
  const navigate = useNavigate();
  const location = useLocation();
  const state = location.state as ConfirmedBooking | null;

  // If someone lands here directly (refresh, bookmark) without a booking
  // in navigation state, send them back rather than showing a blank page.
  useEffect(() => {
    if (!state) navigate('/book');
  }, [state, navigate]);

  if (!state) return null;

  return (
    <div className="page center">
      <div className="success-icon">✓</div>
      <h1>Booking request submitted!</h1>
      <p>
        Reference: <strong>{state.reference}</strong>
      </p>
      <p className="subtitle">
        Thank you. Your booking request has been sent to the Traabco team.
        You will receive a confirmation email within 24 hours.
      </p>

      <div className="summary-card">
        <div className="summary-row"><span>Service</span><span>{state.service.name}</span></div>
        <div className="summary-row"><span>Date</span><span>{state.date} · {state.time}</span></div>
        <div className="summary-row"><span>Consultant</span><span>{state.service.consultant}</span></div>
        <div className="summary-row"><span>Fee</span><span>{formatRand(state.service.fee)} · payable at session</span></div>
      </div>

      <div className="pending-banner">
        Your booking is currently pending. Traabco will review your request and confirm within 24 hours.
      </div>

      <div className="button-row">
        <button type="button" className="btn-primary" onClick={() => navigate('/dashboard')}>
          Back to my account
        </button>
        <button type="button" onClick={() => navigate(`/payments/${state.reference}`)}>
          Payment details
        </button>
      </div>
    </div>
  );
}
