import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../App.css";

export default function ForgotPassword() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [submitted, setSubmitted] = useState(false);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!email.trim()) {
      return;
    }

    setSubmitted(true);
  };

  return (
    <div className="au-page">
      <div className="au-card">

        {/* Header */}
        <header className="au-header">
          <div className="au-header-brand">
            <div className="au-icon-box">
              <svg
                viewBox="0 0 24 24"
                width="20"
                height="20"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              >
                <rect x="3" y="11" width="18" height="11" rx="2" />
                <path d="M7 11V7a5 5 0 0 1 10 0v4" />
              </svg>
            </div>

            <div>
              <div className="au-brand-title">TRAABCO</div>
              <div className="au-brand-sub">
                Management portal · Mthatha, EC
              </div>
            </div>
          </div>

          {/* Back to Login */}
          <button
            type="button"
            className="au-header-back"
            onClick={() => navigate("/")}
          >
            Back to Login
          </button>
        </header>

        {/* Main Content */}
        <main className="au-body">

          {/* Lock Icon */}
          <div className="au-lock">
            <svg
              viewBox="0 0 24 24"
              width="28"
              height="28"
              fill="none"
              stroke="currentColor"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            >
              <rect x="4" y="10" width="16" height="11" rx="2" />
              <path d="M8 10V7a4 4 0 0 1 8 0v3" />
              <circle cx="12" cy="15.5" r="1" />
            </svg>
          </div>

          <h1 className="au-title">
            Forgot your password?
          </h1>

          <p className="au-lead">
            Enter the email address associated with your TRAABCO account.
            We will help you reset your password.
          </p>

          {!submitted ? (
            <form className="au-form" onSubmit={handleSubmit}>

              {/* Email */}
              <label className="au-label" htmlFor="forgot-email">
                Email address <span className="au-required">*</span>
              </label>

              <input
                id="forgot-email"
                className="au-input"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Enter your email address"
                required
              />

              <p className="au-hint">
                Enter the email address you used when registering your
                TRAABCO account.
              </p>

              {/* Submit */}
              <button
                type="submit"
                className="au-btn"
              >
                Continue
              </button>

              {/* Back to Login */}
              <p className="au-center">
                Remember your password?{" "}
                <button
                  type="button"
                  className="au-link"
                  onClick={() => navigate("/")}
                >
                  Return to Login
                </button>
              </p>
            </form>
          ) : (
            <div className="au-form">

              {/* Success Message */}
              <div className="au-verified">
                <div>
                  <div className="au-verified-title">
                    Email address received
                  </div>

                  <div className="au-verified-sub">
                    Your password reset request has been received.
                  </div>
                </div>
              </div>

              <p className="au-hint">
                A password reset link has been prepared for{" "}
                <strong>{email}</strong>.
              </p>

              {/* Continue to Reset Password */}
              <button
                type="button"
                className="au-btn"
                onClick={() => navigate("/reset-password", { state: { email } })}
              >
                Continue to Reset Password
              </button>

              {/* Return to Login */}
              <p className="au-center">
                <button
                  type="button"
                  className="au-link"
                  onClick={() => navigate("/")}
                >
                  Return to Login
                </button>
              </p>
            </div>
          )}

          {/* Manual Help */}
          <div className="au-help">
            <h2 className="au-help-title">
              Need help?
            </h2>

            <p>
              If you no longer have access to your registered email
              address, please contact TRAABCO support for assistance.
            </p>
          </div>
        </main>

        {/* Footer */}
        <footer className="au-footer">
          <p className="au-footer-line">
            <button
              type="button"
              className="au-link"
              onClick={() => navigate("/")}
            >
              Return to Login
            </button>
          </p>
        </footer>

      </div>
    </div>
  );
}