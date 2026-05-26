import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { API_URL } from '../config';

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const { token, user, logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const response = await fetch(`${API_URL}/api/orders`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch orders');
      }

      const data = await response.json();
      setOrders(data);
      setLoading(false);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '20px' }}>
      <div style={{ 
        display: 'flex', 
        justifyContent: 'space-between', 
        alignItems: 'center',
        marginBottom: '30px',
        paddingBottom: '20px',
        borderBottom: '2px solid #2563eb'
      }}>
        <div>
          <h1 style={{ color: '#2563eb', margin: 0 }}>CloudStore</h1>
          <p style={{ color: '#666', margin: '5px 0 0 0' }}>Welcome, {user?.name || user?.email}!</p>
        </div>
        <div style={{ display: 'flex', gap: '10px' }}>
          <button
            onClick={() => navigate('/products')}
            style={{
              padding: '10px 20px',
              background: 'white',
              color: '#2563eb',
              border: '2px solid #2563eb',
              borderRadius: '4px',
              cursor: 'pointer',
              fontWeight: '500'
            }}
          >
            Products
          </button>
          <button
            onClick={handleLogout}
            style={{
              padding: '10px 20px',
              background: '#dc2626',
              color: 'white',
              border: 'none',
              borderRadius: '4px',
              cursor: 'pointer',
              fontWeight: '500'
            }}
          >
            Logout
          </button>
        </div>
      </div>

      <h2 style={{ marginBottom: '20px' }}>My Orders</h2>

      {loading && <p>Loading orders...</p>}

      {error && (
        <div style={{
          padding: '15px',
          background: '#fee',
          color: '#c00',
          borderRadius: '8px',
          marginBottom: '20px'
        }}>
          Error: {error}
        </div>
      )}

      {!loading && !error && orders.length === 0 && (
        <p style={{ color: '#666' }}>You have no orders yet.</p>
      )}

      {!loading && !error && orders.length > 0 && (
        <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
          {orders.map(order => (
            <div key={order.id} style={{
              border: '1px solid #e5e7eb',
              borderRadius: '8px',
              padding: '20px',
              background: 'white'
            }}>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <div>
                  <strong>Order #{order.id}</strong>
                  <p style={{ margin: '5px 0', color: '#666' }}>
                    Product ID: {order.productId}
                  </p>
                  <p style={{ margin: '5px 0', color: '#666' }}>
                    Quantity: {order.quantity}
                  </p>
                </div>
                <div style={{ textAlign: 'right' }}>
                  <span style={{
                    display: 'inline-block',
                    padding: '5px 15px',
                    background: order.status === 'pending' ? '#fef3c7' : 
                                order.status === 'shipped' ? '#dbeafe' : '#d1fae5',
                    color: order.status === 'pending' ? '#92400e' : 
                           order.status === 'shipped' ? '#1e40af' : '#065f46',
                    borderRadius: '999px',
                    fontSize: '14px',
                    fontWeight: '500'
                  }}>
                    {order.status}
                  </span>
                  <p style={{ margin: '10px 0 0 0', color: '#666', fontSize: '14px' }}>
                    {new Date(order.createdAt).toLocaleDateString()}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}