import React, { useState, useRef } from 'react';
import { Navigate, useNavigate, Link } from 'react-router-dom';
import { useAuth } from '@/context/AuthContext';
import { useToast } from '@/hooks/use-toast';
import { LogIn } from 'lucide-react';
import { Button } from '@/components/ui/button';
import MainLayout from '@/components/Layout/MainLayout';

const Login = () => {
  const { user, login, isLoading, error: authError } = useAuth();
  const { toast } = useToast();
  const navigate = useNavigate();
  
  const [role, setRole] = useState<'user' | 'admin'>('user');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [formError, setFormError] = useState('');
  const emailRef = useRef<HTMLInputElement>(null);

  // Auto-focus on email field
  React.useEffect(() => {
    if (emailRef.current) {
      emailRef.current.focus();
    }
  }, []);

  // Redirect if already logged in
  if (user) {
    return <Navigate to="/dashboard" replace />;
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setFormError('');

    if (!email || !password) {
      setFormError('Please enter both email and password');
      return;
    }

    // console.log("Mocking login for UI testing...");
    
    //We need to manually set the user in AuthContext or force navigation
    
    //just force the browser to go to the dashboard.
    // navigate('/dashboard'); 
    // return;

    try {
      const success = await login(email, password, role, captchaToken);
      if (success) {
        setFailedAttempts(0);
        toast({
          title: "Login successful!",
          description: "Welcome to the Grievance Management System",
        });
        navigate('/dashboard');
      } else {
        setFailedAttempts(prev => prev + 1);
        if (failedAttempts + 1 >= MAX_ATTEMPTS) {
          setIsLocked(true);
          setFormError('Account temporarily locked due to too many failed attempts. Please try again in 1 minute.');
          lockoutTimer.current = setTimeout(() => {
            setIsLocked(false);
            setFailedAttempts(0);
          }, LOCKOUT_TIME);
        } else {
          setFormError(authError || 'Invalid credentials. Please try again.');
        }
      }
    } catch (error) {
      setFailedAttempts(prev => prev + 1);
      setFormError('A network or server error occurred. Please try again.');
      console.error(error);
    }
  };

  return (
    <MainLayout>
      <div className="max-w-md mx-auto mt-10">
        <div className="bg-card shadow-lg rounded-lg p-8 border border-border">
          <div className="text-center mb-8">
            <div className="inline-flex justify-center items-center w-16 h-16 rounded-full bg-primary/10 mb-4">
              <LogIn className="h-8 w-8 text-primary" />
            </div>
            <h1 className="text-2xl font-bold">Login</h1>
            <p className="text-muted-foreground">Access your grievance portal account</p>
          </div>

          {formError && (
            <div className="bg-red-50 text-red-800 p-3 rounded-md mb-4 text-sm" role="alert">
              {formError}
            </div>
          )}

          {authError && !formError && (
            <div className="bg-yellow-50 text-yellow-800 p-3 rounded-md mb-4 text-sm" role="alert">
              {authError}
            </div>
          )}

          <form onSubmit={handleSubmit}>
            <div className="form-input-wrapper">
              <label htmlFor="email" className="form-label">Email</label>
              <input
                id="email"
                type="email"
                ref={emailRef}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="form-input"
                placeholder="your@email.com"
                disabled={isLoading}
              />
            </div>

            <div className="form-input-wrapper">
              <label htmlFor="password" className="form-label">Password</label>
              <input
                id="password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="form-input"
                placeholder="********"
                disabled={isLoading}
              />
            </div>

            <div className="form-input-wrapper mb-6">
              <label className="form-label mb-2">Login As</label>
              <div className="flex gap-4">
                <label className="flex items-center gap-2 cursor-pointer">
                  <input
                    type="radio"
                    name="role"
                    value="user"
                    checked={role === 'user'}
                    onChange={() => setRole('user')}
                    className="accent-primary h-4 w-4"
                  />
                  <span>User</span>
                </label>
                <label className="flex items-center gap-2 cursor-pointer">
                  <input
                    type="radio"
                    name="role"
                    value="admin"
                    checked={role === 'admin'}
                    onChange={() => setRole('admin')}
                    className="accent-primary h-4 w-4"
                  />
                  <span>Admin</span>
                </label>
              </div>
            </div>

            <Button 
              type="submit" 
              className="w-full"
              disabled={isLoading}
            >
              {isLoading ? 'Logging in...' : 'Login'}
            </Button>
          </form>

          <div className="mt-6 text-center text-sm">
            <p className="text-muted-foreground mb-4">
              Don't have an account?{' '}
              <Link to="/signup" className="text-primary hover:underline">
                Sign up
              </Link>
            </p>
          </div>
          
          <div className="border-t pt-4 mt-4 text-xs text-muted-foreground">
            <p className="mb-2">Demo credentials:</p>
            <div className="grid grid-cols-2 gap-4">
              <div className="bg-muted p-2 rounded">
                <strong>Admin</strong><br/>
                admin@gmail.com<br/>
                admin
              </div>
              <div className="bg-muted p-2 rounded">
                <strong>User</strong><br/>
                user@gmail.com<br/>
                user
              </div>
            </div>
          </div>
        </div>
      </div>
    </MainLayout>
  );
};

export default Login;