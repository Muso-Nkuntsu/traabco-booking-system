import { render, screen } from '@testing-library/react';
import App from './App';

test('opens on the TRAABCO sign-in page', () => {
  render(<App />);
  expect(screen.getByText(/sign in to your account/i)).toBeInTheDocument();
  expect(screen.getByRole('button', { name: /^sign in$/i })).toBeInTheDocument();
});
