import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom'; // 1. Added useLocation
// @ts-ignore
import '../App.css'

export default function Login() {
  const navigate = useNavigate();
  const location = useLocation(); // 2. Initialize the location listener hook

  // 3. Fallback to placeholder default if no state exists, otherwise grab the passed email
  const registrationState = location.state as { registeredEmail?: string } | null;
  const initialEmail = registrationState?.registeredEmail || 'lusindiso@traabco.co.za';

  const [email, setEmail] = useState(initialEmail);
  const [password, setPassword] = useState('password123');

  // 4. Update input state immediately if registration state changes later
  useEffect(() => {
    if (registrationState?.registeredEmail) {
      setEmail(registrationState.registeredEmail);
    }
  }, [registrationState]);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
   e.preventDefault();

    // 1. Check if the logging-in user is the TRAABCO staff member
    if (email === 'admin@traabco.co.za' && password === 'admin123') {
      alert("Welcome back, TRAABCO Administrator.");
      
      // Send them to the special Admin section
      navigate('/admin');
    } else {
      alert(`Welcome back, ${email}!`);
      
      // Send standard business owners to their normal Client Account view
      navigate('/dashboard');
    }
  };

  return (
    <div className="lg-page">
      <div className="lg-frame">
        <span className="lg-frame-indicator">Frame</span>

        {/* Brand Banner Heading Section */}
        <div className="lg-brand-section">
          <div className="lg-icon-box">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" strokeWidth="2.5" strokeLinecap="round" strokeLinejoin="round">
              <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
              <path d="M7 11V7a5 5 0 0 1 10 0v4" />
            </svg>
          </div>
          <div>
            <h1 className="lg-brand-title">TRAABCO</h1>
            <p className="lg-brand-sub">Management portal · Mthatha, EC</p>
          </div>
        </div>

        {/* Input Interactive Form Area */}
        <form className="lg-form" onSubmit={handleSubmit}>
          <h2 className="lg-form-title">Sign in to your account</h2>
          <p className="lg-form-subtitle">Tacks Registered Accountants & Business Consultants</p>

          <div className="lg-input-group">
            <label htmlFor="email">Email address</label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="lg-input-group">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <div className="lg-forgot-row" style={{ display: 'flex', justifyContent: 'space-between' }}>
            <button type="button" className="lg-link-btn" onClick={() => navigate('/register')}>
              Create an account
            </button>
            <button type="button"
            className="lg-link-btn"
            onClick={() => navigate('/forgot-password')}
            >
              Forgot password?
              </button>
          </div>

          <button type="submit" className="lg-submit-btn">
            Sign in
          </button>
        </form>

        {/* Footer Meta Data Labels */}
        <footer className="lg-footer">
          <div className="lg-access-levels">
            Access levels: Admin · Consultant · Viewer
          </div>
          <div className="lg-address">
            No. 83 Madeira Street, Mthatha · Est. 2012
          </div>
        </footer>
      </div>
    </div>
  );
}
