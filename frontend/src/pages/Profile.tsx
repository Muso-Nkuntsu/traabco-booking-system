import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../App.css';
import { passwordProblems } from '../utils/password';

// 1. Structure the incoming data properties passed down from App.js states
interface ProfileProps {
  businessName: string; setBusinessName: (v: string) => void;
  industry: string; setIndustry: (v: string) => void;
  cipcNumber: string; setCipcNumber: (v: string) => void;
  vatNumber: string; setVatNumber: (v: string) => void;
  address: string; setAddress: (v: string) => void;
  townCity: string; setTownCity: (v: string) => void;
  fullName: string; setFullName: (v: string) => void;
  position: string; setPosition: (v: string) => void;
  email: string;
  phone: string; setPhone: (v: string) => void;
}

export default function Profile({
  businessName, setBusinessName, industry, setIndustry, cipcNumber, setCipcNumber,
  vatNumber, setVatNumber, address, setAddress, townCity, setTownCity,
  fullName, setFullName, position, setPosition, email, phone, setPhone
}: ProfileProps) {
  const navigate = useNavigate();

  // Local state parameters for security management
  const [currentPassword, setCurrentPassword] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmNewPassword, setConfirmNewPassword] = useState('');

  const handleSaveChanges = (e: React.FormEvent) => {
    e.preventDefault();
    if (newPassword) {
      if (!currentPassword) {
        alert("Please enter your current password to set a new one.");
        return;
      }
      if (newPassword !== confirmNewPassword) {
        alert("Security Error: New passwords do not match!");
        return;
      }
      const problem = passwordProblems(newPassword);
      if (problem) {
        alert(problem);
        return;
      }
    }
    alert("Profile settings and compliance records updated successfully!");
    navigate('/dashboard');
  };

  return (
    <div className="rb-page">
      <div className="rb-frame" style={{ minHeight: '680px' }}>
        <span className="rb-frame-indicator">MY ACCOUNT PORTAL</span>
        
        {/* Header Navigation Banner */}
        <header className="rb-header">
          <div className="rb-brand">TRAABCO</div>
          <div className="rb-header-divider">|</div>
          <div className="rb-header-sub">Client Profile Settings</div>
          <button type="button" className="rb-signin-link" onClick={() => navigate('/dashboard')}>
            &larr; Back to Dashboard
          </button>
        </header>

        <form className="rb-grid-container" style={{ marginTop: '24px' }} onSubmit={handleSaveChanges}>
          {/* Left Form Settings Column */}
          <div className="rb-form-column">
            
            {/* SUBHEADING 1: BUSINESS REGISTRY */}
            <h2 className="rb-section-title" style={{ borderBottom: '1px solid #eee', paddingBottom: '6px' }}>
              1. CORPORATE REGISTRY DETAILS
            </h2>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Company / business name</label>
                <input type="text" value={businessName} onChange={(e) => setBusinessName(e.target.value)} required />
              </div>
              <div className="rb-input-group">
                <label>Industry sector</label>
                <input type="text" value={industry} onChange={(e) => setIndustry(e.target.value)} required />
              </div>
            </div>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>CIPC registration number</label>
                <input type="text" value={cipcNumber} onChange={(e) => setCipcNumber(e.target.value)} />
              </div>
              <div className="rb-input-group">
                <label>SARS VAT number</label>
                <input type="text" value={vatNumber} onChange={(e) => setVatNumber(e.target.value)} />
              </div>
            </div>
            <div className="rb-field-row">
              <div className="rb-input-group" style={{ flex: 2 }}>
                <label>Business physical address</label>
                <input type="text" value={address} onChange={(e) => setAddress(e.target.value)} required />
              </div>
              <div className="rb-input-group">
                <label>Town / city</label>
                <input type="text" value={townCity} onChange={(e) => setTownCity(e.target.value)} required />
              </div>
            </div>

            {/* SUBHEADING 2: PRIMARY CONTACT */}
            <h2 className="rb-section-title" style={{ borderBottom: '1px solid #eee', paddingBottom: '6px', marginTop: '16px' }}>
              2. PRIMARY REPRESENTATIVE
            </h2>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Full name</label>
                <input type="text" value={fullName} onChange={(e) => setFullName(e.target.value)} required />
              </div>
              <div className="rb-input-group">
                <label>Corporate role / position</label>
                <input type="text" value={position} onChange={(e) => setPosition(e.target.value)} required />
              </div>
            </div>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Account login email (read-only)</label>
                <input type="email" value={email} disabled style={{ background: '#f5f5f5', color: '#777', cursor: 'not-allowed' }} />
              </div>
              <div className="rb-input-group">
                <label>Contact phone number</label>
                <input type="tel" value={phone} onChange={(e) => setPhone(e.target.value)} required />
              </div>
            </div>

            {/* SUBHEADING 3: PORTAL SECURITY */}
            <h2 className="rb-section-title" style={{ borderBottom: '1px solid #eee', paddingBottom: '6px', marginTop: '16px' }}>
              3. CHANGE PASSWORD (OPTIONAL)
            </h2>
            <div className="rb-field-row">
              <div className="rb-input-group">
                <label>Current password</label>
                <input type="password" placeholder="••••••••" value={currentPassword} onChange={(e) => setCurrentPassword(e.target.value)} />
              </div>
              <div className="rb-input-group">
                <label>New password</label>
                <input type="password" placeholder="••••••••" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} />
              </div>
              <div className="rb-input-group">
                <label>Confirm new password</label>
                <input type="password" placeholder="••••••••" value={confirmNewPassword} onChange={(e) => setConfirmNewPassword(e.target.value)} />
              </div>
            </div>

            {/* Profile Bottom Form Control Actions */}
            <div className="rb-form-actions" style={{ marginTop: '28px' }}>
              <button type="button" className="rb-cancel-btn" onClick={() => navigate('/dashboard')}>
                Discard Changes
              </button>
              <button type="submit" className="rb-next-btn">
                Save Profile Updates ✓
              </button>
            </div>
          </div>

          {/* Right Information Help Sidebar Column Anchor */}
          <aside className="rb-sidebar-column">
            <h3 className="rb-panel-heading">COMPLIANCE REVIEW</h3>
            <p className="rb-panel-description">
              Modifying registry values or CIPC indicators flags your profile for validation review. Discrepancies may temporarily halt scheduled SARS tax submission queues until data points match the official government records.
            </p>
            <h3 className="rb-panel-heading" style={{ marginTop: '16px' }}>SECURITY ALERT</h3>
            <p className="rb-panel-description">
              Passwords require standard verification checks. Never distribute login strings to unauthorized representatives.
            </p>
          </aside>
        </form>
      </div>
    </div>
  );
}
