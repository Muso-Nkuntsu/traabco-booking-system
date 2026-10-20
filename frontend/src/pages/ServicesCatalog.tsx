import { useNavigate } from 'react-router-dom';
import '../App.css';
import availableServices, { formatRand } from '../data/services';

export default function ServicesCatalog() {
  const navigate = useNavigate();

  const handleBookRedirect = (serviceId: string) => {
    // Navigate straight to your Booking portal, passing the preset ID to automate step 1 selection
    navigate('/book', { state: { autoSelectId: serviceId } });
  };

  return (
    <div className="rb-page">
      <div className="rb-frame" style={{ minHeight: '600px' }}>
        <span className="rb-frame-indicator">TRAABCO SERVICE PORTAL</span>
        
        {/* Header Ribbon Navigation Banner */}
        <header className="rb-header">
          <div className="rb-brand">TRAABCO</div>
          <div className="rb-header-divider">|</div>
          <div className="rb-header-sub">Corporate Service Catalogue</div>
          <button type="button" className="rb-signin-link" onClick={() => navigate('/dashboard')}>
            &larr; Back to Dashboard
          </button>
        </header>

        {/* Catalog Introduction Layout */}
        <div className="pd-title-row" style={{ marginTop: '20px', borderBottom: '1px solid #eee', paddingBottom: '14px' }}>
          <div>
            <h1 className="pd-title" style={{ fontSize: '16px' }}>Professional Accounting & Consulting Provisions</h1>
            <p className="pd-subtitle">Select a tailored professional solution to review parameters and secure an appointment slot with our director board.</p>
          </div>
        </div>

        {/* Services Showcase Layout Grid Grid */}
        <main className="service-grid" style={{ gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))', gap: '20px', marginTop: '24px' }}>
          {availableServices.map((srv) => (
            <div 
              key={srv.id} 
              className="service-card" 
              style={{ cursor: 'default', display: 'flex', flexDirection: 'column', justifyContent: 'space-between', minHeight: '190px' }}
            >
              <div>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '8px' }}>
                  <span className="pd-label" style={{ fontSize: '9px', background: '#e2e8f0', padding: '2px 6px', borderRadius: '3px' }}>
                    {srv.category}
                  </span>
                  <span className="service-meta" style={{ fontWeight: 600, color: '#111' }}>
                    {formatRand(srv.fee)}
                  </span>
                </div>
                
                <h3 className="service-name" style={{ fontSize: '14px', margin: '4px 0 6px' }}>{srv.name}</h3>
                <p className="service-meta" style={{ fontSize: '11px', color: '#555', lineHeight: '14px', marginBottom: '12px' }}>
                  {srv.description}
                </p>
              </div>

              <div style={{ borderTop: '1px dashed #e2e8f0', paddingTop: '10px', marginTop: 'auto', display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <div className="service-meta">
                  <span>{srv.consultant}</span> &middot; <strong>{srv.durationMinutes} min</strong>
                </div>
                <button 
                  type="button" 
                  className="rb-next-btn"
                  style={{ height: '26px', fontSize: '10px', padding: '0 12px', borderRadius: '3px' }}
                  onClick={() => handleBookRedirect(srv.id)}
                >
                  Book Now &rarr;
                </button>
              </div>
            </div>
          ))}
        </main>
      </div>
    </div>
  );
}
