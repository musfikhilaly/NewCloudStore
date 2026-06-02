import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { USER_SERVICE_URL } from '../config';
import Navbar from '../components/Navbar';

export default function Orders() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const { token, user } = useAuth();
    const navigate = useNavigate();

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = async () => {
        try {
            const response = await fetch(`${USER_SERVICE_URL}/api/orders`, {
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (!response.ok) throw new Error('Failed to fetch orders');
            const data = await response.json();
            setOrders(data);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };

    return (
        <div style={{ minHeight: '100vh', background: '#f7fafc' }}>
            <Navbar />

            <div style={{ maxWidth: '900px', margin: '0 auto', padding: '40px 20px' }}>
                <h2 style={{ fontSize: '32px', fontWeight: '700', color: '#2d3748', marginBottom: '30px' }}>
                    My Orders
                </h2>

                {loading && <p style={{ color: '#718096' }}>Loading orders...</p>}

                {error && (
                    <div style={{ padding: '15px', background: '#fff5f5', color: '#c53030', borderRadius: '8px', marginBottom: '20px' }}>
                        Error: {error}
                    </div>
                )}

                {!loading && !error && orders.length === 0 && (
                    <div style={{ textAlign: 'center', padding: '60px 20px' }}>
                        <p style={{ fontSize: '18px', color: '#718096', marginBottom: '20px' }}>You have no orders yet.</p>
                        <button
                            onClick={() => navigate('/products')}
                            style={{ padding: '12px 30px', background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', color: 'white', border: 'none', borderRadius: '10px', fontSize: '16px', fontWeight: '600', cursor: 'pointer' }}
                        >
                            Start Shopping
                        </button>
                    </div>
                )}

                {!loading && !error && orders.length > 0 && (
                    <div style={{ display: 'flex', flexDirection: 'column', gap: '16px' }}>
                        {orders.map(order => (
                            <div key={order.id} style={{ border: '1px solid #e2e8f0', borderRadius: '12px', padding: '24px', background: 'white', boxShadow: '0 2px 8px rgba(0,0,0,0.05)' }}>
                                <div style={{ display: 'flex', flexDirection: 'column', gap: '8px' }}>
                                    <strong style={{ fontSize: '18px', color: '#2d3748' }}>
                                        Thank you for your order, {user?.email}!
                                    </strong>
                                    <p style={{ margin: '0', color: '#718096', fontSize: '14px' }}>Order #{order.id}</p>
                                    <p style={{ margin: '0', color: '#718096', fontSize: '14px' }}>Product ID: {order.productId}</p>
                                    <p style={{ margin: '0', color: '#718096', fontSize: '14px' }}>Quantity: {order.quantity}</p>
                                    <p style={{ margin: '0', color: '#718096', fontSize: '13px' }}>
                                        {new Date(order.createdAt).toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' })}
                                    </p>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>

            <footer style={{
                background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                color: 'white',
                padding: '40px 20px',
                marginTop: '60px',
                textAlign: 'center'
            }}>
                <h3 style={{ fontSize: '24px', fontWeight: '700', marginBottom: '8px' }}>CloudStore</h3>
                <p style={{ opacity: 0.8, marginBottom: '16px' }}>Your one-stop shop for amazing products</p>
                <p style={{ opacity: 0.6, fontSize: '14px' }}>© 2026 CloudStore. All rights reserved.</p>
            </footer>
        </div>
    );
}