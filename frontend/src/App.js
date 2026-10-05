import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import BookAService from "./pages/BookAService";
import BookingSuccess from "./pages/BookingSuccess";
import BookingFailed from "./pages/BookingFailed";
import PaymentDetail from "./pages/PaymentDetail"; 
import MyAccount from "./pages/MyAccount";
import Login from "./pages/Login";
import Profile from "./pages/Profile";
import ServicesCatalog from "./pages/ServicesCatalog";
import Engagements from "./pages/Engagements";
import AdminDashboard from "./pages/AdminDashboard"; // 1. Imported Admin Dashboard file
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";

// Import registration sub-pages
import RegisterBusinessDetails from "./pages/RegisterBusinessDetails";
import RegisterContactPerson from "./pages/RegisterContactPerson";
import RegisterPassword from "./pages/RegisterPassword";

import "./App.css";

function App() {
  const [userBookings, setUserBookings] = useState([]);

  // Lifted registration states to carry data across routes seamlessly
  const [businessName, setBusinessName] = useState('Kaya Spaza Shop');
  const [industry, setIndustry] = useState('Retail');
  const [cipcNumber, setCipcNumber] = useState('2018/112345/07');
  const [vatNumber, setVatNumber] = useState('');
  const [address, setAddress] = useState('14 Madeira Street, Mthatha, 5099');
  const [townCity, setTownCity] = useState('Mthatha');
  const [servicesNeeded, setServicesNeeded] = useState('Accounting & bookkeeping');
  const [fullName, setFullName] = useState('');
  const [position, setPosition] = useState('');
  const [email, setEmail] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  return (
    <BrowserRouter>
      <Routes>
        {/* Gateway Authentication Routes */}
        <Route path="/" element={<Login />} />
        
        {/* 2. Added the Master Admin Management Route Link Mapping */}
        <Route 
          path="/admin" 
          element={<AdminDashboard bookings={userBookings} setBookings={setUserBookings} />} 
        />

        {/* Client Side Dashboards */}
        <Route path="/dashboard" element={<MyAccount bookings={userBookings} />} />
        <Route path="/services" element={<ServicesCatalog />} />
        <Route path="/engagements" element={<Engagements />} />
        
        <Route path="/profile" element={
          <Profile 
            businessName={businessName} setBusinessName={setBusinessName}
            industry={industry} setIndustry={setIndustry}
            cipcNumber={cipcNumber} setCipcNumber={setCipcNumber}
            vatNumber={vatNumber} setVatNumber={setVatNumber}
            address={address} setAddress={setAddress}
            townCity={townCity} setTownCity={setTownCity}
            fullName={fullName} setFullName={setFullName}
            position={position} setPosition={setPosition}
            email={email}
            phone={phone} setPhone={setPhone}
          />
        } />

        {/* Registration Workflow Steps */}

<Route path="/forgot-password" element={<ForgotPassword />} />

<Route path="/reset-password" element={<ResetPassword />} />

<Route path="/dashboard" element={<MyAccount bookings={userBookings} />} />
        {/* Step 1 Route */}
        <Route path="/register" element={
          <RegisterBusinessDetails 
            businessName={businessName} setBusinessName={setBusinessName}
            industry={industry} setIndustry={setIndustry}
            cipcNumber={cipcNumber} setCipcNumber={setCipcNumber}
            vatNumber={vatNumber} setVatNumber={setVatNumber}
            address={address} setAddress={setAddress}
            townCity={townCity} setTownCity={setTownCity}
            servicesNeeded={servicesNeeded} setServicesNeeded={setServicesNeeded}
          />
        } />
        <Route path="/register/contact" element={
          <RegisterContactPerson 
            fullName={fullName} setFullName={setFullName}
            position={position} setPosition={setPosition}
            email={email} setEmail={setEmail}
            phone={phone} setPhone={setPhone}
          />
        } />
        <Route path="/register/password" element={
          <RegisterPassword 
            businessName={businessName} fullName={fullName} email={email}
            password={password} setPassword={setPassword}
            confirmPassword={confirmPassword} setConfirmPassword={setConfirmPassword}
          />
        } />

        {/* Consultation Action Workspaces */}
        <Route path="/book" element={<BookAService bookings={userBookings} setBookings={setUserBookings} />} />
        <Route path="/payments/:bookingId" element={<PaymentDetail bookings={userBookings} />} />
        <Route path="/book/success" element={<BookingSuccess />} />
        <Route path="/book/failed" element={<BookingFailed />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
