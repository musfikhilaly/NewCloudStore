import { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { PRODUCT_SERVICE_URL } from '../config';
import Navbar from '../components/Navbar';
import ProductCard from '../components/ProductCard';

export default function Products() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { token } = useAuth();

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await fetch(`${PRODUCT_SERVICE_URL}/api/products`,  {
        headers: { 'Authorization': `Bearer ${token}` }
      });

      if (!response.ok) throw new Error('Failed to fetch products');

      const data = await response.json();
      setProducts(data);
      setLoading(false);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  return (
    <div style={{ minHeight: '100vh', background: '#f7fafc' }}>
      <Navbar />

      <div style={{
        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        color: 'white',
        padding: '60px 20px',
        textAlign: 'center'
      }}>
        <h1 style={{
          fontSize: '48px',
          fontWeight: '700',
          marginBottom: '16px',
          textShadow: '0 2px 4px rgba(0,0,0,0.1)'
        }}>
          Discover Amazing Products
        </h1>
        <p style={{
          fontSize: '20px',
          opacity: 0.9,
          maxWidth: '600px',
          margin: '0 auto'
        }}>
          Shop the latest trends and exclusive deals
        </p>
      </div>

      <div style={{
        maxWidth: '1400px',
        margin: '0 auto',
        padding: '60px 20px'
      }}>
        {loading && (
          <div style={{ textAlign: 'center', padding: '60px 0' }}>
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
            <p style={{ marginTop: '20px', color: '#718096' }}>Loading products...</p>
          </div>
        )}

        {error && (
          <div style={{
            padding: '20px',
            background: '#fff5f5',
            color: '#c53030',
            borderRadius: '12px',
            textAlign: 'center',
            maxWidth: '500px',
            margin: '0 auto'
          }}>
            <strong>Error:</strong> {error}
          </div>
        )}

        {!loading && !error && (
          <div style={{
            display: 'grid',
            gridTemplateColumns: 'repeat(auto-fill, minmax(280px, 1fr))',
            gap: '30px'
          }}>
            {products.map(product => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        )}

          {/* Footer */}
          <footer style={{
              background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
              color: 'white',
              padding: '40px 20px',
              textAlign: 'center'
          }}>
              <h3 style={{ fontSize: '24px', fontWeight: '700', marginBottom: '8px' }}>CloudStore</h3>
              <p style={{ opacity: 0.8, marginBottom: '16px' }}>Your one-stop shop for amazing products</p>
              <p style={{ opacity: 0.6, fontSize: '14px' }}>© 2026 CloudStore. All rights reserved.</p>
          </footer>
      </div>
    </div>
  );
}