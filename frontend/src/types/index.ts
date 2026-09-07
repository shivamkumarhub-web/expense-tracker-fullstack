export interface JwtResponse {
  token: string;
  type: string;
  username: string;
  roles: string[];
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface Expense {
  id: number;
  description: string;
  amount: number;
  type: 'INCOME' | 'EXPENSE';
  expenseDate: string;
  categoryName: string | null;
  createdAt: string;
}

export interface ExpenseRequest {
  description: string;
  amount: number;
  type: 'INCOME' | 'EXPENSE';
  expenseDate: string;
  categoryId?: number;
}

export interface Summary {
  totalIncome: number;
  totalExpense: number;
  balance: number;
  transactionCount: number;
}

export interface Category {
  id: number;
  name: string;
  icon: string | null;
}