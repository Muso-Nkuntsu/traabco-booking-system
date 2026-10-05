import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// @ts-ignore
import '../App.css';

interface EngagementRecord {
  id: string;
  clientName: string;
  type: string;
  financialYear: string;
  leadPartner: string;
  status: 'Planning' | 'Fieldwork' | 'Review' | 'Signed Off';
  progressPercentage: number;
  letterSigned: boolean;
  pbcUploaded: number;
  pbcTotal: number;
}

const activeEngagements: EngagementRecord[] = [
  {
    id: 'ENG-2026-004',
    clientName: 'Kaya Spaza Shop',
    type: 'Independent Financial Review Engagement',
    financialYear: 'FY2026',
    leadPartner: 'L. Takatshana (CA/RA)',
    status: 'Fieldwork',
    progressPercentage: 45,
    letterSigned: true,
    pbcUploaded: 6,
    pbcTotal: 9
  },
  {
    id: 'ENG-2026-001',
    clientName: 'Kaya Spaza Shop',
    type: 'Annual SARS Corporate Tax Compliance & Filing',
    financialYear: 'FY2025',
    leadPartner: 'T. Jikijela (GTP/SA)',
    status: 'Review',
    progressPercentage: 90,
    letterSigned: true,
    pbcUploaded: 12,
    pbcTotal: 12
  }
];

export default function Engagements() {
  const navigate = useNavigate();
  const [selectedEng, setSelectedEng] = useState<string>(activeEngagements[0]?.id || '');

  const current = activeEngagements.find(e => e.id === selectedEng) || activeEngagements[0];

  return (
    <div className="rb-page">
      <div className="rb-frame" style={{ minHeight: '660px' }}>
        <span className="rb-frame-indicator">TRAABCO COMPLIANCE ASSURANCE</span>
        
        {/* Header Navigation Banner */}
        <header className="rb-header">
          <div className="rb-brand">TRAABCO</div>
          <div className="rb-header-divider">|</div>
          <div className="rb-header-sub">Corporate Engagement Mandates</div>
          <button type="button" className="rb-signin-link" onClick={() => navigate('/dashboard')}>
            &larr; Back to Dashboard
          </button>
        </header>

        {/* Title row */}
        <div className="pd-title-row" style={{ marginTop: '20px' }}>
          <div>
            <h1 className="pd-title" style={{ fontSize: '16px' }}>Active Compliance & Assurance Engagements</h1>
            <p className="pd-subtitle">Monitor audit pipelines, sign engagement mandates, and track pending financial document uploads for SARS timelines.</p>
          </div>
        </div>

        {/* Two-Column Matrix Grid */}
        <main className="rb-grid-container" style={{ gridTemplateColumns: '320px 1fr', gap: '30px', marginTop: '16px' }}>
          
          {/* Left Column: Engagement Mandate List Selector */}
          <div className="rb-form-column" style={{ gap: '12px' }}>
            <h3 className="rb-panel-heading">Active Engagements</h3>
            {activeEngagements.map((eng) => (
              <div 
                key={eng.id}
                onClick={() => setSelectedEng(eng.id)}
                className={`service-card ${selectedEng === eng.id ? 'selected' : ''}`}
                style={{ padding: '14px', borderRadius: '6px', cursor: 'pointer' }}
              >
                <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '10px', color: '#666', marginBottom: '4px' }}>
                  <span>{eng.id}</span>
                  <strong>{eng.financialYear}</strong>
                </div>
                <h4 className="service-name" style={{ fontSize: '12px', margin: '2px 0 6px' }}>{eng.type}</h4>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginTop: '10px' }}>
                  <span className="service-meta" style={{ fontSize: '10px' }}>Partner: {eng.leadPartner.split(' ')[0]}</span>
                  <span className="pd-pill pd-pill-sm" style={{ 
                    background: eng.status === 'Review' ? '#e6f1fb' : eng.status === 'Fieldwork' ? '#fef3c7' : '#e2e8f0',
                    color: eng.status === 'Review' ? '#0c447c' : eng.status === 'Fieldwork' ? '#b45309' : '#333'
                  }}>
                    {eng.status}
                  </span>
                </div>
              </div>
            ))}
          </div>

          {/* Right Column: Expanded Milestone & PBC Tracker View */}
          {current && (
            <div className="rb-form-column" style={{ background: '#f8fafc', padding: '20px', borderRadius: '8px', border: '1px solid #e2e8f0' }}>
              <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', borderBottom: '1px dashed #cbd5e1', paddingBottom: '12px', marginBottom: '16px' }}>
                <div>
                  <h2 style={{ fontSize: '14px', margin: 0, fontWeight: 600, color: '#042c53' }}>{current.type}</h2>
                  <p style={{ fontSize: '11px', color: '#555', margin: '4px 0 0' }}>Assigned Partner: {current.leadPartner}</p>
                </div>
                <span className="pd-pill pd-pill-lg" style={{ background: '#e3eed9', color: '#2b5a1f' }}>{current.financialYear}</span>
              </div>

              {/* Progress Bar Subsection */}
              <div style={{ marginBottom: '20px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', fontSize: '11px', fontWeight: 500, marginBottom: '6px' }}>
                  <span>Engagement Lifecycle Progress</span>
                  <span>{current.progressPercentage}%</span>
                </div>
                <div style={{ width: '100%', height: '8px', background: '#e2e8f0', borderRadius: '999px', overflow: 'hidden' }}>
                  <div style={{ width: `${current.progressPercentage}%`, height: '100%', background: '#0c447c', borderRadius: '999px', transition: 'width 0.4s' }} />
                </div>
              </div>

              {/* Mandate Compliance Checklists */}
              <div className="rb-field-row" style={{ gap: '20px', marginBottom: '14px' }}>
                <div className="service-card" style={{ flex: 1, cursor: 'default', background: '#fff' }}>
                  <span className="pd-label" style={{ fontSize: '10px', color: '#666' }}>1. LEGAL LETTER MANDATE</span>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginTop: '8px', fontSize: '12px', fontWeight: 600 }}>
                    <span style={{ color: current.letterSigned ? '#2b5a1f' : '#b91c1c' }}>{current.letterSigned ? '✓ Signed' : '⚠ Outstanding'}</span>
                  </div>
                  <p style={{ fontSize: '10px', color: '#666', margin: '4px 0 0' }}>CIPC authorization complete</p>
                </div>

                <div className="service-card" style={{ flex: 1, cursor: 'default', background: '#fff' }}>
                  <span className="pd-label" style={{ fontSize: '10px', color: '#666' }}>2. PROVIDED BY CLIENT (PBC) INFO</span>
                  <div style={{ display: 'flex', alignItems: 'center', gap: '8px', marginTop: '8px', fontSize: '12px', fontWeight: 600 }}>
                    <span>{current.pbcUploaded} / {current.pbcTotal} Files Audit-Ready</span>
                  </div>
                  <p style={{ fontSize: '10px', color: '#1a5aa8', margin: '4px 0 0', cursor: 'pointer', textDecoration: 'underline' }}>Upload pending audits</p>
                </div>
              </div>

              {/* Timeline Track Steps Indicator Block */}
              <div style={{ marginTop: '10px', background: '#fff', border: '1px solid #e2e8f0', padding: '14px', borderRadius: '6px' }}>
                <h4 style={{ fontSize: '11px', margin: '0 0 10px', textTransform: 'uppercase', color: '#333', letterSpacing: '0.04em' }}>Milestone Target Audits</h4>
                <div style={{ display: 'flex', flexDirection: 'column', gap: '8px', fontSize: '11px' }}>
                  <div style={{ display: 'flex', gap: '8px', color: '#2b5a1f', fontWeight: 500 }}>✓ Phase 1: Planning & Risk Matrix Strategy Completed</div>
                  <div style={{ display: 'flex', gap: '8px', color: current.status !== 'Planning' ? '#2b5a1f' : '#666' }}>
                    {current.status === 'Planning' ? '○' : '✓'} Phase 2: Substantive Verification Fieldwork (Ledgers & Trial Balances)
                  </div>
                  <div style={{ display: 'flex', gap: '8px', color: current.status === 'Review' || current.status === 'Signed Off' ? '#2b5a1f' : '#666' }}>
                    {current.status === 'Review' ? '● In Progress:' : '○'} Phase 3: Director Concurrence Evaluation & Final Reporting
                  </div>
                </div>
              </div>

            </div>
          )}
        </main>
      </div>
    </div>
  );
}
