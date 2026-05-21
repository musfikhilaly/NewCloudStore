import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import Navbar from '../components/Navbar';

export default function ProductDetail() {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  const { token } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    fetchProductWithOrders();
  }, [id]);

  const fetchProductWithOrders = async () => {
    try {
      const response = await fetch(`/api/products/${id}/with-orders`, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });

      if (!response.ok) {
        throw new Error('Failed to fetch product details');
      }

      const result = await response.json();
      setData(result);
      setLoading(false);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div style={{ minHeight: '100vh', background: '#f7fafc' }}>
        <Navbar />
        <div style={{ textAlign: 'center', padding: '100px 20px' }}>
          <div style={{
            width: '50px',
            height: '50px',
            border: '4px solid #e2e8f0',
            borderTop: '4px solid #667eea',
            borderRadius: '50%',
            margin: '0 auto',
            animation: 'spin 1s linear infinite'
          }}></div>
          <style>{`@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }`}</style>
          <p style={{ marginTop: '20px', color: '#718096' }}>Loading product...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div style={{ minHeight: '100vh', background: '#f7fafc' }}>
        <Navbar />
        <div style={{ maxWidth: '600px', margin: '100px auto', padding: '20px' }}>
          <div style={{
            padding: '20px',
            background: '#fff5f5',
            color: '#c53030',
            borderRadius: '12px',
            textAlign: 'center'
          }}>
            <strong>Error:</strong> {error}
          </div>
          <button
            onClick={() => navigate('/products')}
            style={{
              marginTop: '20px',
              padding: '12px 24px',
              background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
              color: 'white',
              border: 'none',
              borderRadius: '8px',
              cursor: 'pointer',
              fontWeight: '600'
            }}
          >
            Back to Products
          </button>
        </div>
      </div>
    );
  }

  if (!data) {
    return (
      <div style={{ minHeight: '100vh', background: '#f7fafc' }}>
        <Navbar />
        <div style={{ maxWidth: '600px', margin: '100px auto', padding: '20px', textAlign: 'center' }}>
          <p style={{ color: '#718096' }}>Product not found</p>
          <button
            onClick={() => navigate('/products')}
            style={{
              marginTop: '20px',
              padding: '12px 24px',
              background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
              color: 'white',
              border: 'none',
              borderRadius: '8px',
              cursor: 'pointer',
              fontWeight: '600'
            }}
          >
            Back to Products
          </button>
        </div>
      </div>
    );
  }

  return (
    <div style={{ minHeight: '100vh', background: '#f7fafc' }}>
      <Navbar />

      <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '40px 20px' }}>
        <button
          onClick={() => navigate('/products')}
          style={{
            padding: '10px 20px',
            marginBottom: '30px',
            background: 'white',
            color: '#667eea',
            border: '2px solid #667eea',
            borderRadius: '8px',
            cursor: 'pointer',
            fontWeight: '600',
            fontSize: '14px'
          }}
        >
          ← Back to Products
        </button>

        <div style={{
          background: 'white',
          borderRadius: '16px',
          padding: '40px',
          boxShadow: '0 4px 12px rgba(0,0,0,0.08)',
          marginBottom: '40px'
        }}>
          <div style={{ display: 'flex', gap: '60px', flexWrap: 'wrap' }}>
            <div style={{ flex: '0 0 400px' }}>
              <img
                src={data.image}
                alt={data.title}
                style={{
                  width: '100%',
                  height: '400px',
                  objectFit: 'contain',
                  background: '#f8f9fa',
                  borderRadius: '12px',
                  padding: '20px'
                }}
              />
            </div>

            <div style={{ flex: '1', minWidth: '300px' }}>
              <div style={{
                display: 'inline-block',
                padding: '6px 16px',
                background: 'rgba(102, 126, 234, 0.1)',
                color: '#667eea',
                borderRadius: '20px',
                fontSize: '12px',
                fontWeight: '600',
                textTransform: 'uppercase',
                marginBottom: '16px'
              }}>
                {data.category}
              </div>

              <h1 style={{
                fontSize: '32px',
                fontWeight: '700',
                color: '#2d3748',
                marginBottom: '20px',
                lineHeight: '1.3'
              }}>
                {data.title}
              </h1>

              <div style={{
                fontSize: '40px',
                fontWeight: '700',
                background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                WebkitBackgroundClip: 'text',
                WebkitTextFillColor: 'transparent',
                marginBottom: '24px'
              }}>
                ${data.price}
              </div>

              <p style={{
                color: '#4a5568',
                fontSize: '16px',
                lineHeight: '1.7',
                marginBottom: '30px'
              }}>
                {data.description}
              </p>

              <button
                style={{
                  padding: '16px 40px',
                  background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                  color: 'white',
                  border: 'none',
                  borderRadius: '12px',
                  fontSize: '16px',
                  fontWeight: '600',
                  cursor: 'pointer',
                  boxShadow: '0 4px 12px rgba(102, 126, 234, 0.4)'
                }}
              >
                Add to Cart
              </button>
            </div>
          </div>
        </div>

        <div style={{
          background: 'white',
          borderRadius: '16px',
          padding: '40px',
          boxShadow: '0 4px 12px rgba(0,0,0,0.08)'
        }}>
          <h2 style={{
            fontSize: '24px',
            fontWeight: '700',
            color: '#2d3748',
            marginBottom: '24px'
          }}>
            Orders for this Product
          </h2>

          {data.orders.length === 0 ? (
            <p style={{ color: '#718096' }}>No orders yet for this product.</p>
          ) : (
            <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
              {data.orders.map(order => (
                <div key={order.id} style={{
                  border: '1px solid #e2e8f0',
                  borderRadius: '12px',
                  padding: '20px',
                  background: '#f8f9fa'
                }}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                    <div>
                      <strong style={{ fontSize: '16px', color: '#2d3748' }}>Order #{order.id}</strong>
                      <p style={{ margin: '8px 0', color: '#718096', fontSize: '14px' }}>
                        Quantity: {order.quantity}
                      </p>
                      <p style={{ margin: '0', color: '#718096', fontSize: '14px' }}>
                        {new Date(order.createdAt).toLocaleDateString('en-US', { 
                          year: 'numeric', 
                          month: 'long', 
                          day: 'numeric' 
                        })}
                      </p>
                    </div>
                    <div>
                      <span style={{
                        display: 'inline-block',
                        padding: '8px 20px',
                        background: order.status === 'pending' ? '#fef3c7' : 
                                    order.status === 'shipped' ? '#dbeafe' : '#d1fae5',
                        color: order.status === 'pending' ? '#92400e' : 
                               order.status === 'shipped' ? '#1e40af' : '#065f46',
                        borderRadius: '20px',
                        fontSize: '14px',
                        fontWeight: '600',
                        textTransform: 'capitalize'
                      }}>
                        {order.status}
                      </span>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}