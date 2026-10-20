// Shared password rules so registration, profile and reset all agree.
export interface PasswordRule {
  label: string;
  met: boolean;
}

export function checkPassword(password: string): PasswordRule[] {
  return [
    { label: 'At least 8 characters', met: password.length >= 8 },
    { label: 'One uppercase letter (A–Z)', met: /[A-Z]/.test(password) },
    { label: 'One number (0–9)', met: /[0-9]/.test(password) },
    { label: 'One special character (!@#$%)', met: /[^A-Za-z0-9]/.test(password) },
  ];
}

export function isStrongPassword(password: string): boolean {
  return checkPassword(password).every((r) => r.met);
}

// Returns a readable message listing unmet rules, or null if all are met.
export function passwordProblems(password: string): string | null {
  const unmet = checkPassword(password).filter((r) => !r.met);
  if (unmet.length === 0) return null;
  return 'Password needs: ' + unmet.map((r) => r.label.toLowerCase()).join(', ') + '.';
}
