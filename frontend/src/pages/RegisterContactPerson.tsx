import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';

interface Step2Props {
  fullName: string; setFullName: (v: string) => void;
  position: string; setPosition: (v: string) => void;
  email: string; setEmail: (v: string) => void;
  phone: string; setPhone: (v: string) => void;
}

export default function RegisterContactPerson({
  fullName, setFullName, position, setPosition, email, setEmail, phone, setPhone
}: Step2Props) {
  const navigate = useNavigate();

  const handleNext = (e: React.FormEvent) => {
    e.preventDefault();
    navigate('/register/password');
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
          <div className="rb-step active"><span className="rb-step-num">2</span><span className="rb-step-text">Contact person</span></div>
          <div className="rb-step"><span className="rb-step-num inactive">3</span><span className="rb-step-text">Set password</span></div>
        </div>

        <form className="rb-grid-container" onSubmit={handleNext}>
          <div className="rb-form-column">
            <h2 className="rb-section-title">STEP 2 — CONTACT PERSON INFORMATION</h2>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Full name <span className="rb-required">*</span></label>
                <input type="text" placeholder="e.g. Liyema Nkuntsu" value={fullName} onChange={(e) => setFullName(e.target.value)} required />
              </div>
              <div className="rb-input-group">
                <label>Position / role <span className="rb-required">*</span></label>
                <input type="text" placeholder="e.g. Managing Director / Owner" value={position} onChange={(e) => setPosition(e.target.value)} required />
              </div>
            </div>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Email address <span className="rb-required">*</span></label>
                <input type="email" placeholder="name@company.co.za" value={email} onChange={(e) => setEmail(e.target.value)} required />
              </div>
              <div className="rb-input-group">
                <label>Phone number <span className="rb-required">*</span></label>
                <input type="tel" placeholder="e.g. 047 531 0010" value={phone} onChange={(e) => setPhone(e.target.value)} required />
              </div>
            </div>
            <div className="rb-form-actions">
              <button type="button" className="rb-cancel-btn" onClick={() => navigate('/register')}>&larr; Back</button>
              <button type="submit" className="rb-next-btn">Next: Set password &rarr;</button>
            </div>
          </div>

          <aside className="rb-sidebar-column">
            <h3 className="rb-panel-heading">PRIMARY CONTACT</h3>
            <p className="rb-panel-description">This individual will act as the primary account administrator. Traabco consultants will coordinate tax signatures and session updates directly with them.</p>
          </aside>
        </form>
      </div>
    </div>
  );
}
