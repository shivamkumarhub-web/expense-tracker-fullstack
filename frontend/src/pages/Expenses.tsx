import { useEffect, useState } from 'react';
import api from '../api/client';
import { Expense, ExpenseRequest } from '../types';
import { Plus, Trash2, Edit } from 'lucide-react';

export default function Expenses() {
  const [expenses, setExpenses] = useState<Expense[]>([]);
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState<ExpenseRequest>({
    description: '', amount: 0, type: 'EXPENSE', expenseDate: new Date().toISOString().split('T')[0]
  });

  const loadExpenses = () => api.get('/expenses').then(({ data }) => setExpenses(data));

  useEffect(() => { loadExpenses(); }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await api.post('/expenses', form);
    setForm({ description: '', amount: 0, type: 'EXPENSE', expenseDate: new Date().toISOString().split('T')[0] });
    setShowForm(false);
    loadExpenses();
  };

  const handleDelete = async (id: number) => {
    await api.delete(`/expenses/${id}`);
    loadExpenses();
  };

  return (
    <div>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-gray-800">Expenses</h1>
        <button onClick={() => setShowForm(!showForm)} className="flex items-center gap-1 bg-indigo-600 text-white px-4 py-2 rounded-lg hover:bg-indigo-700">
          <Plus size={18} /> Add
        </button>
      </div>

      {showForm && (
        <form onSubmit={handleSubmit} className="bg-white p-6 rounded-xl shadow-sm border mb-6 grid grid-cols-1 md:grid-cols-4 gap-4">
          <input type="text" placeholder="Description" value={form.description}
            onChange={(e) => setForm({ ...form, description: e.target.value })}
            className="px-3 py-2 border rounded-lg" required />
          <input type="number" placeholder="Amount" value={form.amount || ''}
            onChange={(e) => setForm({ ...form, amount: Number(e.target.value) })}
            className="px-3 py-2 border rounded-lg" required />
          <select value={form.type} onChange={(e) => setForm({ ...form, type: e.target.value as 'INCOME' | 'EXPENSE' })}
            className="px-3 py-2 border rounded-lg">
            <option value="EXPENSE">Expense</option>
            <option value="INCOME">Income</option>
          </select>
          <input type="date" value={form.expenseDate} onChange={(e) => setForm({ ...form, expenseDate: e.target.value })}
            className="px-3 py-2 border rounded-lg" />
          <button type="submit" className="md:col-span-4 bg-indigo-600 text-white py-2 rounded-lg hover:bg-indigo-700">Save</button>
        </form>
      )}

      <div className="bg-white rounded-xl shadow-sm border overflow-hidden">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-4 py-3 text-left text-sm font-medium text-gray-600">Description</th>
              <th className="px-4 py-3 text-left text-sm font-medium text-gray-600">Type</th>
              <th className="px-4 py-3 text-left text-sm font-medium text-gray-600">Amount</th>
              <th className="px-4 py-3 text-left text-sm font-medium text-gray-600">Date</th>
              <th className="px-4 py-3 text-left text-sm font-medium text-gray-600">Actions</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-200">
            {expenses.map((exp) => (
              <tr key={exp.id} className="hover:bg-gray-50">
                <td className="px-4 py-3 text-sm text-gray-800">{exp.description}</td>
                <td className="px-4 py-3"><span className={exp.type === 'INCOME' ? 'text-green-600 text-sm font-medium' : 'text-red-600 text-sm font-medium'}>{exp.type}</span></td>
                <td className="px-4 py-3 text-sm font-medium text-gray-800">&#8377;{Number(exp.amount).toLocaleString()}</td>
                <td className="px-4 py-3 text-sm text-gray-600">{exp.expenseDate}</td>
                <td className="px-4 py-3">
                  <button onClick={() => handleDelete(exp.id)} className="text-red-600 hover:text-red-800"><Trash2 size={16} /></button>
                </td>
              </tr>
            ))}
            {expenses.length === 0 && (
              <tr><td colSpan={5} className="px-4 py-8 text-center text-gray-500">No expenses yet. Click Add to create one.</td></tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}