import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';

interface Step1Props {
  businessName: string; setBusinessName: (v: string) => void;
  industry: string; setIndustry: (v: string) => void;
  cipcNumber: string; setCipcNumber: (v: string) => void;
  vatNumber: string; setVatNumber: (v: string) => void;
  address: string; setAddress: (v: string) => void;
  townCity: string; setTownCity: (v: string) => void;
  servicesNeeded: string; setServicesNeeded: (v: string) => void;
}

export default function RegisterBusinessDetails({
  businessName, setBusinessName, industry, setIndustry, cipcNumber, setCipcNumber,
  vatNumber, setVatNumber, address, setAddress, townCity, setTownCity, servicesNeeded, setServicesNeeded
}: Step1Props) {
  const navigate = useNavigate();

  const handleNext = (e: React.FormEvent) => {
    e.preventDefault();
    navigate('/register/contact');
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
          <div className="rb-step active"><span className="rb-step-num">1</span><span className="rb-step-text">Business details</span></div>
          <div className="rb-step"><span className="rb-step-num inactive">2</span><span className="rb-step-text">Contact person</span></div>
          <div className="rb-step"><span className="rb-step-num inactive">3</span><span className="rb-step-text">Set password</span></div>
        </div>

        <form className="rb-grid-container" onSubmit={handleNext}>
          <div className="rb-form-column">
            <h2 className="rb-section-title">STEP 1 — BUSINESS INFORMATION</h2>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Company / business name <span className="rb-required">*</span></label>
                <input type="text" value={businessName} onChange={(e) => setBusinessName(e.target.value)} required />
              </div>
              <div className="rb-input-group">
                <label>Industry / sector <span className="rb-required">*</span></label>
                <input type="text" value={industry} onChange={(e) => setIndustry(e.target.value)} required />
              </div>
            </div>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>CIPC registration number</label>
                <input type="text" value={cipcNumber} onChange={(e) => setCipcNumber(e.target.value)} />
                <span className="rb-helper-text">Optional — leave blank if not yet registered</span>
              </div>
              <div className="rb-input-group">
                <label>VAT number</label>
                <input type="text" placeholder="Optional" value={vatNumber} onChange={(e) => setVatNumber(e.target.value)} />
                <span className="rb-helper-text">Only if your business is VAT registered</span>
              </div>
            </div>
            <div className="rb-input-group full-width">
              <label>Business address <span className="rb-required">*</span></label>
              <input type="text" value={address} onChange={(e) => setAddress(e.target.value)} required />
            </div>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Town / city <span className="rb-required">*</span></label>
                <input type="text" value={townCity} onChange={(e) => setTownCity(e.target.value)} required />
              </div>
              <div className="rb-input-group">
                <label>Services needed <span className="rb-required">*</span></label>
                <input type="text" value={servicesNeeded} onChange={(e) => setServicesNeeded(e.target.value)} required />
              </div>
            </div>
            <div className="rb-form-actions">
              <button type="button" className="rb-cancel-btn" onClick={() => navigate('/')}>Cancel</button>
              <button type="submit" className="rb-next-btn">Next: Contact person →</button>
            </div>
          </div>

          <aside className="rb-sidebar-column">
            <h3 className="rb-panel-heading">WHY WE NEED THIS</h3>
            <p className="rb-panel-description">Your CIPC registration number and VAT number allow Traabco to correctly prepare your tax submissions and compliance documents for SARS.</p>
            <div className="rb-options-block">
              <h4 className="rb-options-title">Industry options</h4>
              <p className="rb-options-list">
                {['Retail', 'Wholesale', 'Construction', 'Food services', 'Transport', 'Professional services', 'Agriculture', 'Other'].map((ind) => (
                  <span key={ind} onClick={() => setIndustry(ind)}>{ind} · </span>
                ))}
              </p>
            </div>
          </aside>
        </form>
      </div>
    </div>
  );
}
