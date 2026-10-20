import React, { useEffect, useMemo, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import '../App.css';
import { checkPassword } from '../utils/password';

const LINK_LIFETIME_MINUTES = 23; // replace with the real expiry from your API

export default function ResetPassword() {
  const navigate = useNavigate();
  const location = useLocation();
  const state = location.state as { email?: string } | null;
  const email = state?.email || new URLSearchParams(location.search).get('email') || 'owner@kayaspaza.co.za';

  const [password, setPassword] = useState('');
  const [confirm, setConfirm] = useState('');
  const [secondsLeft, setSecondsLeft] = useState(LINK_LIFETIME_MINUTES * 60);

  useEffect(() => {
    const id = setInterval(() => setSecondsLeft((s) => Math.max(0, s - 1)), 1000);
    return () => clearInterval(id);
  }, []);

  const rules = useMemo(() => checkPassword(password), [password]);

  const metCount = rules.filter((r) => r.met).length;
  const strength =
    password.length === 0
      ? { label: '', level: 0 }
      : metCount === 4
      ? { label: 'Strong', level: 4 }
      : metCount === 3
      ? { label: 'Good', level: 3 }
      : metCount === 2
      ? { label: 'Fair', level: 2 }
      : { label: 'Weak', level: 1 };

  const allMet = metCount === rules.length;
  const matches = confirm.length > 0 && password === confirm;
  const expired = secondsLeft === 0;
  const minutesLeft = Math.ceil(secondsLeft / 60);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (!allMet || !matches || expired) return;
    // TODO: send the new password + reset token to your API, then sign the user in.
    navigate('/dashboard');
  };

  return (
    <div className="au-page">
      <div className="au-card">
        <header className="au-header">
          <div className="au-header-brand">
            <div className="au-icon-box">
              <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
                <path d="M7 11V7a5 5 0 0 1 10 0v4" />
              </svg>
            </div>
            <div>
              <div className="au-brand-title">TRAABCO</div>
              <div className="au-brand-sub">Tacks Registered Accountants &amp; Business Consultants</div>
            </div>
          </div>
          <button type="button" className="au-header-back" onClick={() => navigate('/')}>
            ← Back to sign in
          </button>
        </header>

        <main className="au-body">
          <div className={`au-verified ${expired ? 'is-expired' : ''}`} role="status">
            <svg viewBox="0 0 24 24" width="22" height="22" fill="none" stroke="currentColor" strokeWidth="1.8" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
              <circle cx="12" cy="12" r="10" />
              {expired ? <path d="M15 9l-6 6M9 9l6 6" /> : <path d="M8 12.5l2.7 2.7L16 9.5" />}
            </svg>
            <div>
              <div className="au-verified-title">
                {expired ? 'This link has expired' : 'Link verified successfully'}
              </div>
              <div className="au-verified-sub">
                {email} ·{' '}
                {expired
                  ? 'Request a new reset link'
                  : `Link expires in ${minutesLeft} minute${minutesLeft === 1 ? '' : 's'}`}
              </div>
            </div>
          </div>

          <h1 className="au-title">Set your new password</h1>
          <p className="au-lead">Choose a strong password to keep your Traabco account secure.</p>

          <form className="au-form" onSubmit={handleSubmit}>
            <label htmlFor="rp-new" className="au-label">
              New password <span className="au-required">*</span>
            </label>
            <input
              id="rp-new"
              type="password"
              className="au-input"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              autoComplete="new-password"
              required
            />

            <div className="au-meter" aria-hidden="true">
              {[1, 2, 3, 4].map((i) => (
                <span key={i} className={`au-meter-seg ${i <= strength.level ? `lvl-${strength.level}` : ''}`} />
              ))}
            </div>
            {strength.label && (
              <p className={`au-strength lvl-${strength.level}`}>Password strength: {strength.label}</p>
            )}

            <div className="au-rules">
              <p className="au-rules-title">Requirements</p>
              <ul>
                {rules.map((r) => (
                  <li key={r.label} className={r.met ? 'met' : 'unmet'}>
                    <span className="au-dot" aria-hidden="true" />
                    {r.label}
                    <span className="au-sr">{r.met ? ' (met)' : ' (not met)'}</span>
                  </li>
                ))}
              </ul>
            </div>

            <label htmlFor="rp-confirm" className="au-label">
              Confirm new password <span className="au-required">*</span>
            </label>
            <input
              id="rp-confirm"
              type="password"
              className="au-input"
              value={confirm}
              onChange={(e) => setConfirm(e.target.value)}
              autoComplete="new-password"
              required
            />

            {confirm.length > 0 && (
              <div className={`au-match ${matches ? 'ok' : 'bad'}`} role="status">
                <svg viewBox="0 0 24 24" width="16" height="16" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" aria-hidden="true">
                  <circle cx="12" cy="12" r="10" />
                  {matches ? <path d="M8 12.5l2.7 2.7L16 9.5" /> : <path d="M15 9l-6 6M9 9l6 6" />}
                </svg>
                {matches ? 'Passwords match' : 'Passwords do not match'}
              </div>
            )}

            <button type="submit" className="au-btn" disabled={!allMet || !matches || expired}>
              Set new password and sign in
            </button>
            <p className="au-note">
              After resetting, you will be signed in automatically and taken to your account.
            </p>
          </form>
        </main>

        <footer className="au-footer">
          <p className="au-footer-line">
            Need help? Call <a href="tel:0475310000">047 531 0000</a> · No. 83 Madeira Street, Mthatha
          </p>
        </footer>
      </div>
    </div>
  );
}