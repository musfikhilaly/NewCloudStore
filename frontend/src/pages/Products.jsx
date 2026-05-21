import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

export default function Products() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const { token, user, logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await fetch('http://localhost:8081/products', {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch products');
      }

      const data = await response.json();
      setProducts(data);
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
            onClick={() => navigate('/orders')}
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
            My Orders
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

      <h2 style={{ marginBottom: '20px' }}>Products</h2>

      {loading && <p>Loading products...</p>}

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

      {!loading && !error && (
        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))',
          gap: '20px'
        }}>
          {products.slice(0, 12).map(product => (
            <div key={product.id} style={{
              border: '1px solid #e5e7eb',
              borderRadius: '8px',
              padding: '15px',
              background: 'white'
            }}>
              <img
                src={product.image}
                alt={product.title}
                style={{
                  width: '100%',
                  height: '200px',
                  objectFit: 'contain',
                  marginBottom: '10px'
                }}
              />
              <h3 style={{
                fontSize: '14px',
                margin: '0 0 10px 0',
                height: '40px',
                overflow: 'hidden'
              }}>
                {product.title}
              </h3>
              <p style={{
                fontSize: '20px',
                fontWeight: 'bold',
                color: '#2563eb',
                margin: '0'
              }}>
                ${product.price}
              </p>
              <span style={{
                display: 'inline-block',
                marginTop: '10px',
                padding: '4px 10px',
                background: '#f3f4f6',
                color: '#6b7280',
                fontSize: '12px',
                borderRadius: '999px'
              }}>
                {product.category}
              </span>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}