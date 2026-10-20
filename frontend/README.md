# TRAABCO Business Platform — Frontend (wireframes)

React + TypeScript client portal for **Tacks Registered Accountants & Business Consultants (TRAABCO)**, Mthatha.
Built with Create React App (`react-scripts` 5) and React Router 7.

## Run it

```bash
npm install
npm start        # http://localhost:3000
npm test         # runs src/App.test.js
npm run build    # production build in /build
```

## Screens and routes

| Route | Screen |
|---|---|
| `/` | Sign in (`admin@traabco.co.za` / `admin123` opens the admin board; anything else opens the client account) |
| `/register` → `/register/contact` → `/register/password` | 3-step business registration |
| `/forgot-password` → `/reset-password` | Password recovery |
| `/dashboard` | My account — bookings list with status filters |
| `/profile` | Edit business, contact and password details |
| `/services` | Service catalogue ("Book Now" pre-selects the service) |
| `/book` → `/book/success` or `/book/failed` | Book a service |
| `/payments/:bookingId` | Payment details for one booking |
| `/engagements` | Compliance / assurance engagement tracker |
| `/admin` | Admin scheduling board — confirm pending bookings |

## Project layout

```
src/
  App.js                 routes + shared state (registration details, bookings)
  data/services.ts       the one list of services, fees and consultants
  types/index.ts         shared types (Service, Booking, …)
  utils/password.ts      password rules used by register, profile and reset
  component/             ConfirmBookingModal
  pages/                 one file per screen
```

## Wireframe limitations (until the Spring Boot backend is connected)

- Sign-in is not checked against real accounts, and `/admin` is not protected.
- Bookings live in memory only — refreshing the browser clears them.
- Booking submission is simulated and fails ~30% of the time on purpose so the failure screen can be tested (`fakeSubmitBooking` in `BookAService.tsx`).
