import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';
import { checkPassword, passwordProblems } from '../utils/password';

interface Step3Props {
  businessName: string;
  fullName: string;
  email: string;
  password: string; setPassword: (v: string) => void;
  confirmPassword: string; setConfirmPassword: (v: string) => void;
}

export default function RegisterPassword({
  businessName, fullName, email, password, setPassword, confirmPassword, setConfirmPassword
}: Step3Props) {
  const navigate = useNavigate();

  const handleFinalSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      alert("Passwords do not match!");
      return;
    }
    const problem = passwordProblems(password);
    if (problem) {
      alert(problem);
      return;
    }

    alert(`Account registration request submitted for ${businessName}! Welcome, ${fullName}.`);
    navigate('/', { state: { registeredEmail: email } });
  };

  return (
    <div className="rb-page">
      <div className="rb-frame">
        <span className="rb-frame-indicator">REGISTER MY BUSINESS</span>
        <header className="rb-header">
          <div className="rb-brand">TRAABCO</div>
          <div className="rb-header-divider">|</div>
          <div className="rb-header-sub">Register your business</div>
          <button type="button" className="rb-signin-link" onClick={() => navigate('/')}>Already registered? Sign in</button>
        </header>

        <div className="rb-progress-bar">
          <div className="rb-step"><span className="rb-step-num">1</span><span className="rb-step-text">Business details</span></div>
          <div className="rb-step"><span className="rb-step-num">2</span><span className="rb-step-text">Contact person</span></div>
          <div className="rb-step active"><span className="rb-step-num">3</span><span className="rb-step-text">Set password</span></div>
        </div>

        <form className="rb-grid-container" onSubmit={handleFinalSubmit}>
          <div className="rb-form-column">
            <h2 className="rb-section-title">STEP 3 — SET SECURITY CREDENTIALS</h2>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label htmlFor="rb-pass">Account password <span className="rb-required">*</span></label>
                <input id="rb-pass" type="password" placeholder="••••••••" value={password} onChange={(e) => setPassword(e.target.value)} required />
                <span className="rb-helper-text">
                  {checkPassword(password).map((r) => (
                    <span key={r.label} style={{ display: 'block', color: r.met ? '#2b5a1f' : '#777' }}>
                      {r.met ? '✓' : '○'} {r.label}
                    </span>
                  ))}
                </span>
              </div>
              <div className="rb-input-group">
                <label htmlFor="rb-confirm">Confirm password <span className="rb-required">*</span></label>
                <input id="rb-confirm" type="password" placeholder="••••••••" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
                <span className="rb-helper-text">Re-enter your password selection to prevent input typos</span>
              </div>
            </div>
            <div className="rb-form-actions">
              <button type="button" className="rb-cancel-btn" onClick={() => navigate('/register/contact')}>&larr; Back</button>
              <button type="submit" className="rb-next-btn">Submit Registration &rarr;</button>
            </div>
          </div>

          <aside className="rb-sidebar-column">
            <h3 className="rb-panel-heading">PORTAL SECURITY</h3>
            <p className="rb-panel-description">Your password ensures secure file-sharing channels. Once registered, your corporate data profile is validated against the SARS registry within <strong>24 hours</strong>.</p>
          </aside>
        </form>
      </div>
    </div>
  );
}
