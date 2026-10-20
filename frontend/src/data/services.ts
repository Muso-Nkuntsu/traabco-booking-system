import { Service } from '../types';

// Single source of truth for services, fees and consultants.
// Used by both the Services catalogue and the Book a service page.
const services: Service[] = [
  {
    id: 'acc',
    name: 'Accounting & bookkeeping',
    category: 'Accounting',
    durationMinutes: 90,
    fee: 2200,
    consultant: 'L. Takatshana',
    description:
      'Compilation of monthly management accounts, general trial balances, ledger maintenance, and financial record auditing structures.',
  },
  {
    id: 'tax',
    name: 'Tax services',
    category: 'Tax',
    durationMinutes: 60,
    fee: 1800,
    consultant: 'T. Jikijela',
    description:
      'SARS compliance preparation, provisional tax returns, CIT/PIT calculations, and electronic filing declaration handoffs.',
  },
  {
    id: 'con',
    name: 'Business consulting',
    category: 'Consulting',
    durationMinutes: 60,
    fee: 2800,
    consultant: 'L. Takatshana',
    description:
      'Strategic planning sessions, operational framework modeling, performance advisory, and corporate systems restructuring advice.',
  },
  {
    id: 'aud',
    name: 'Auditing & independent review',
    category: 'Accounting',
    durationMinutes: 120,
    fee: 3200,
    consultant: 'N. Mhlontlo',
    description:
      'Formal company independent review engagement, assurance checks, compliance verification, and substantive financial statement reviews.',
  },
];

export function findService(id: string | undefined | null): Service | undefined {
  return services.find((s) => s.id === id);
}

export function formatRand(amount: number): string {
  // "R 2 200.00" — South African style with a space as the thousands separator
  const [whole, cents] = amount.toFixed(2).split('.');
  return `R ${whole.replace(/\B(?=(\d{3})+(?!\d))/g, ' ')}.${cents}`;
}

export default services;
