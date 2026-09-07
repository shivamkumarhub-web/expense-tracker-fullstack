import { useEffect, useState } from 'react';
import api from '../api/client';
import { Summary } from '../types';
import { TrendingUp, TrendingDown, Wallet, Receipt } from 'lucide-react';

export default function Dashboard() {
  const [summary, setSummary] = useState<Summary | null>(null);
  const now = new Date();

  useEffect(() => {
    api.get('/expenses/summary', { params: { year: now.getFullYear(), month: now.getMonth() + 1 } })
      .then(({ data }) => setSummary(data))
      .catch(console.error);
  }, []);

  if (!summary) return <div className="text-center py-8 text-gray-500">Loading...</div>;

  return (
    <div>
      <h1 className="text-2xl font-bold mb-6 text-gray-800">Monthly Summary - {now.toLocaleString('default', { month: 'long' })} {now.getFullYear()}</h1>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <div className="flex items-center gap-3 text-green-600 mb-2"><TrendingUp size={24} /><span className="text-sm font-medium">Income</span></div>
          <p className="text-3xl font-bold text-gray-800">&#8377;{Number(summary.totalIncome).toLocaleString()}</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <div className="flex items-center gap-3 text-red-600 mb-2"><TrendingDown size={24} /><span className="text-sm font-medium">Expenses</span></div>
          <p className="text-3xl font-bold text-gray-800">&#8377;{Number(summary.totalExpense).toLocaleString()}</p>
        </div>
        <div className="bg-white p-6 rounded-xl shadow-sm border">
          <div className="flex items-center gap-3 text-indigo-600 mb-2"><Wallet size={24} /><span className="text-sm font-medium">Balance</span></div>
          <p className="text-3xl font-bold text-gray-800">&#8377;{Number(summary.balance).toLocaleString()}</p>
        </div>
      </div>
      <div className="mt-6 bg-white p-6 rounded-xl shadow-sm border flex items-center gap-3">
        <Receipt size={20} className="text-gray-500" />
        <span className="text-gray-700">{summary.transactionCount} transactions this month</span>
      </div>
    </div>
  );
}