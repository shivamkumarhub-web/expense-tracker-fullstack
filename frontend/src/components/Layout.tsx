import { ReactNode } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Wallet, LogOut, LayoutDashboard, Receipt } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

export default function Layout({ children }: { children: ReactNode }) {
  const { username, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div className="min-h-screen flex flex-col">
      <nav className="bg-indigo-600 text-white shadow-lg">
        <div className="max-w-7xl mx-auto px-4 py-3 flex items-center justify-between">
          <Link to="/" className="flex items-center gap-2 font-bold text-lg">
            <Wallet size={24} /> ExpenseTracker
          </Link>
          <div className="flex items-center gap-6">
            <Link to="/" className="flex items-center gap-1 hover:text-indigo-200">
              <LayoutDashboard size={18} /> Dashboard
            </Link>
            <Link to="/expenses" className="flex items-center gap-1 hover:text-indigo-200">
              <Receipt size={18} /> Expenses
            </Link>
            <span className="text-indigo-200">Hi, {username}</span>
            <button onClick={handleLogout} className="flex items-center gap-1 hover:text-indigo-200">
              <LogOut size={18} /> Logout
            </button>
          </div>
        </div>
      </nav>
      <main className="flex-1 max-w-7xl mx-auto w-full px-4 py-8">{children}</main>
    </div>
  );
}