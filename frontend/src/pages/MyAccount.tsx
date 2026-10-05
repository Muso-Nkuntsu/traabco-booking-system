import { useMemo, useState } from "react";
import { useNavigate } from "react-router-dom";
// @ts-ignore
import '../App.css'

export type BookingStatus = "Pending" | "Confirmed" | "Completed" | "Cancelled";

export interface Booking {
  reference: string;
  service: string;
  scope: string;
  date: string;
  time: string;
  location: string;
  consultant: string;
  fee: number;
  status: BookingStatus;
}

export interface MyAccountProps {
  clientName?: string;
  clientMeta?: string;
  clientEmail?: string;
  bookings?: Booking[];
  onViewBooking?: (b: Booking) => void;
  onNavigate?: (target: "account" | "payments" | "services") => void;
}

const FILTERS = ["All", "Pending", "Confirmed", "Completed", "Cancelled"] as const;
type Filter = (typeof FILTERS)[number];

const statusClass: Record<BookingStatus, string> = {
  Pending: "ma-pill-pending",
  Confirmed: "ma-pill-confirmed",
  Completed: "ma-pill-completed",
  Cancelled: "ma-pill-cancelled",
};

const dotClass: Record<BookingStatus, string> = {
  Pending: "ma-dot-amber",
  Confirmed: "ma-dot-blue",
  Completed: "ma-dot-green",
  Cancelled: "ma-dot-red",
};

export default function MyAccount({
  clientName = "Kaya Spaza Shop",
  clientMeta = "Retail \u00b7 Mthatha \u00b7 047 531 0010",
  clientEmail = "owner@kayaspaza.co.za",
  bookings = [], // Default to an empty array so random placeholders disappear completely
  onNavigate,
}: MyAccountProps) {
  const navigate = useNavigate();
  const [filter, setFilter] = useState<Filter>("All");
  const [selected, setSelected] = useState<string | null>(
    bookings[0]?.reference ?? null
  );

  const handleNewBookingClick = () => {
    navigate("/book");
  };

  const counts = useMemo(() => {
    const c: Record<BookingStatus, number> = {
      Pending: 0,
      Confirmed: 0,
      Completed: 0,
      Cancelled: 0,
    };
    bookings.forEach((b) => {
      if (c[b.status] !== undefined) {
        c[b.status] += 1;
      }
    });
    return c;
  }, [bookings]);

  const visible =
    filter === "All" ? bookings : bookings.filter((b) => b.status === filter);

  const initials = clientName
    .split(" ")
    .map((w) => w[0])
    .join("")
    .slice(0, 2)
    .toUpperCase();

  const navItem = (label: string, active = false, onClick?: () => void) => (
    <button
      type="button"
      className={`ma-nav-item${active ? " is-active" : ""}`}
      onClick={onClick}
    >
      {label}
    </button>
  );

  return (
    <div className="ma-shell">
      {/* Sidebar Navigation */}
      <aside className="ma-sidebar">
        <div className="ma-brand">
          <div className="ma-brand-name">TRAABCO</div>
          <div className="ma-brand-sub">Mthatha, EC</div>
        </div>
        <nav className="ma-nav">
          {navItem("Dashboard")}
          <div className="ma-nav-group">ACCOUNT</div>
          {navItem("My account", true, () => onNavigate?.("account"))}
          {navItem("Profile", false, () => navigate('/profile'))}
          <div className="ma-nav-group">SERVICES</div>
          {navItem("Book a service", false, handleNewBookingClick)}
          {navItem("Services", false, () => navigate('/services'))}
         {navItem("Engagements", false, () => navigate('/engagements'))}

          <div className="ma-nav-group">FINANCE</div>
          {navItem("Payments", false, () => onNavigate?.("payments"))}
        </nav>
      </aside>

      {/* Main Panel Content */}
      <main className="ma-main">
        <div className="ma-crumbs">
          <a className="ma-crumb-link" href="#dashboard">
            {"\u2190"} Dashboard
          </a>
          <span className="ma-crumb-sep">/</span>
          <span>My account</span>
        </div>

        <div className="ma-title-row">
          <h1 className="ma-title">My account</h1>
          <div className="ma-title-actions">
            
             <button 
              type="button" 
              className="ma-text-btn"
              onClick={() => navigate('/profile')}
            >
              Edit profile
            </button>
            <button
              type="button"
              className="ma-btn-primary"
              onClick={handleNewBookingClick}
            >
              New booking
            </button>
          </div>
        </div>

        <div className="ma-layout">
          <div className="ma-content">
            {/* Client Profile Card */}
            <section className="ma-section">
              <h2 className="ma-heading">CLIENT</h2>
              <div className="ma-client">
                <div className="ma-avatar">{initials}</div>
                <div>
                  <div className="ma-client-name">{clientName}</div>
                  <div className="ma-client-meta">{clientMeta}</div>
                  <div className="ma-client-meta">{clientEmail}</div>
                </div>
              </div>
            </section>

            {/* Bookings Area */}
            <section className="ma-section">
              <h2 className="ma-heading">
                MY BOOKINGS <span className="ma-count">({bookings.length})</span>
              </h2>

              {/* Status Filter Tabs */}
              <div className="ma-tabs" role="tablist">
                {FILTERS.map((f) => (
                  <button
                    key={f}
                    type="button"
                    role="tab"
                    aria-selected={filter === f}
                    className={`ma-tab${filter === f ? " is-active" : ""}`}
                    onClick={() => setFilter(f)}
                  >
                    {f}
                    <span className="ma-tab-count">
                      {f === "All" ? bookings.length : counts[f]}
                    </span>
                  </button>
                ))}
              </div>
              
              {/* Dynamic Bookings List Layout Area */}
              <div className="ma-booking-list">
                {visible.length === 0 ? (
                  /* Elegant empty view state if there are no bookings matching the active filter */
                  <div style={{ padding: "40px 20px", textAlign: "center", color: "#666" }}>
                    <p style={{ margin: "0 0 16px", fontSize: "14px" }}>No bookings found.</p>
                    <button 
                      type="button" 
                      className="ma-btn-primary" 
                      onClick={handleNewBookingClick}
                      style={{ padding: "8px 16px", fontSize: "12px" }}
                    >
                      Book your first service
                    </button>
                  </div>
                ) : (
                  visible.map((b) => (
                    <div 
                      key={b.reference} 
                      className={`ma-booking-card ${selected === b.reference ? 'is-selected' : ''}`}
                      onClick={() => setSelected(b.reference)}
                    >
                      <div className="ma-booking-header">
                        <span className="ma-ref">{b.reference}</span>
                        <span className={`ma-pill ${statusClass[b.status]}`}>
                          <span className={`ma-dot ${dotClass[b.status]}`}></span>
                          {b.status}
                        </span>
                      </div>
                      <div className="ma-service-title">{b.service}</div>
                      <div className="ma-booking-meta">{b.date} at {b.time}</div>
                    </div>
                  ))
                )}
              </div>
            </section>
          </div>
        </div>
      </main>
    </div>
  );
}
