export type ServiceCategory = 'Accounting' | 'Tax' | 'Consulting';

export interface Service {
  id: string;
  name: string;
  category: ServiceCategory;
  durationMinutes: number;
  fee: number;
  consultant: string;
  description: string;
}

export interface BookingDraft {
  service: Service;
  date: string;
  time: string;
}

export interface ConfirmedBooking extends BookingDraft {
  reference: string;
}

export type BookingStatus = 'Pending' | 'Confirmed' | 'Completed' | 'Cancelled';

// The shape stored in App's shared bookings list.
export interface Booking {
  reference: string;
  serviceId: string;
  service: string;
  scope: string;
  date: string;
  time: string;
  location: string;
  consultant: string;
  fee: number;
  status: BookingStatus;
}
