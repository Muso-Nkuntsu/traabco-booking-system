import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// @ts-ignore
import '../App.css';

// 1. Declare the props interface to receive global bookings data from App.js
interface AdminDashboardProps {
  bookings: any[];
  setBookings: React.Dispatch<React.SetStateAction<any[]>>;
}

export default function AdminDashboard({ bookings, setBookings }: AdminDashboardProps) {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState<'All' | 'Pending' | 'Confirmed'>('All');

  // 2. Action handler to confirm a client's pending booking
  const handleConfirmBooking = (reference: string) => {
    setBookings((prevBookings) =>
      prevBookings.map((b) =>
        b.reference === reference ? { ...b, status: 'Confirmed' } : b
      )
    );
    alert(`Booking ${reference} has been officially Confirmed!`);
  };

  // Filter bookings based on selected admin tab control
  const filteredBookings = bookings.filter((b) => {
    if (activeTab === 'Pending') return b.status === 'Pending';
    if (activeTab === 'Confirmed') return b.status === 'Confirmed';
    return true;
  });

  const pendingCount = bookings.filter((b) => b.status === 'Pending').length;

  return (
    <div className="rb-page">
      <div className="rb-frame" style={{ minHeight: '650px', maxWidth: '1000px' }}>
        <span className="rb-frame-indicator">TRAABCO FIRM OPERATIONS CONTROL</span>

        {/* Admin Header Banner */}
        <header className="rb-header" style={{ background: '#041c38' }}>
          <div className="rb-brand">TRAABCO FIRM ADMIN</div>
          <div className="rb-header-divider">|</div>
          <div className="rb-header-sub">Management Portal Overview</div>
          <button type="button" className="rb-signin-link" onClick={() => navigate('/')}>
            Log Out Securely &rarr;
          </button>
        </header>

        {/* Admin Title Statistics Overview Row */}
        <div className="pd-title-row" style={{ marginTop: '20px', borderBottom: '1px solid #eee', paddingBottom: '12px' }}>
          <div>
            <h1 className="pd-title" style={{ fontSize: '18px', color: '#042c53' }}>Master Scheduling Board</h1>
            <p className="pd-subtitle">Review, track, and authorize client consulting requests submitted across the Eastern Cape grid.</p>
          </div>
          <div className="pd-title-actions">
            <span className="pd-pill pd-pill-lg" style={{ background: pendingCount > 0 ? '#fef3c7' : '#e3eed9', color: pendingCount > 0 ? '#b45309' : '#2b5a1f' }}>
              {pendingCount} Action Required
            </span>
          </div>
        </div>

        {/* Tab Selection Filter */}
        <div className="ma-tabs" role="tablist" style={{ marginTop: '20px', marginBottom: '20px' }}>
          {(['All', 'Pending', 'Confirmed'] as const).map((tab) => (
            <button
              key={tab}
              type="button"
              className={`ma-tab ${activeTab === tab ? 'is-active' : ''}`}
              onClick={() => setActiveTab(tab)}
            >
              {tab} Bookings ({tab === 'All' ? bookings.length : tab === 'Pending' ? pendingCount : bookings.length - pendingCount})
            </button>
          ))}
        </div>

        {/* Bookings Queue Grid Container */}
        <div className="rb-form-column">
          {filteredBookings.length === 0 ? (
            <div style={{ padding: '60px 20px', textAlign: 'center', color: '#777', background: '#f8fafc', borderRadius: '6px', border: '1px dashed #cccccc' }}>
              <p style={{ fontSize: '14px', margin: 0 }}>No matching appointment queues found in this category slot.</p>
            </div>
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '14px' }}>
              {filteredBookings.map((b) => (
                <div 
                  key={b.reference} 
                  className="service-card" 
                  style={{ cursor: 'default', display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '16px', background: b.status === 'Pending' ? '#fffdf5' : '#fff' }}
                >
                  <div>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '12px', marginBottom: '6px' }}>
                      <strong style={{ fontSize: '14px', color: '#0c447c' }}>{b.reference}</strong>
                      <span className="pd-label" style={{ fontSize: '9px', background: '#e2e8f0', padding: '2px 6px', borderRadius: '3px', fontWeight: 600 }}>
                        {b.service}
                      </span>
                      <span style={{ fontSize: '11px', color: '#666' }}>Fee: R {b.fee}.00</span>
                    </div>
                    <p style={{ margin: '2px 0', fontSize: '12px', color: '#222' }}>
                      <strong>Client Profile:</strong> Kaya Spaza Shop
                    </p>
                    <p style={{ margin: '2px 0', fontSize: '11px', color: '#555' }}>
                      <strong>Schedule Timeline:</strong> {b.date} &middot; {b.time} &middot; Assigned Consultant: {b.consultant}
                    </p>
                  </div>

                  {/* Operational Action Control Button */}
                  <div>
                    {b.status === 'Pending' ? (
                      <button
                        type="button"
                        className="rb-next-btn"
                        style={{ height: '32px', background: '#16a34a', borderRadius: '4px' }}
                        onClick={() => handleConfirmBooking(b.reference)}
                      >
                        Confirm Appointment ✓
                      </button>
                    ) : (
                      <span className="pd-pill pd-pill-lg" style={{ background: '#e3eed9', color: '#2b5a1f', padding: '6px 14px' }}>
                        Approved & Active
                      </span>
                    )}
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
