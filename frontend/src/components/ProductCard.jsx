import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { USER_SERVICE_URL } from '../config';

export default function ProductCard({ product }) {
    const [isHovered, setIsHovered] = useState(false);
    const [orderLoading, setOrderLoading] = useState(false);
    const [orderSuccess, setOrderSuccess] = useState(false);
    const navigate = useNavigate();
    const { token, user } = useAuth();

    const handleAddToCart = async (e) => {
        e.stopPropagation();
        setOrderLoading(true);
        try {
            const response = await fetch(`${USER_SERVICE_URL}/api/orders`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userId: user.id,
                    productId: product.id,
                    quantity: 1
                })
            });
            if (!response.ok) throw new Error('Failed');
            setOrderSuccess(true);
            setTimeout(() => setOrderSuccess(false), 2000);
        } catch (err) {
            alert('Failed to add to cart');
        } finally {
            setOrderLoading(false);
        }
    };

    return (
        <div
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
            style={{
                background: 'white',
                borderRadius: '16px',
                overflow: 'hidden',
                cursor: 'pointer',
                transition: 'all 0.3s ease',
                transform: isHovered ? 'translateY(-8px)' : 'translateY(0)',
                boxShadow: isHovered
                    ? '0 20px 40px rgba(0,0,0,0.15)'
                    : '0 4px 12px rgba(0,0,0,0.08)'
            }}
        >
            <div
                onClick={() => navigate(`/products/${product.id}`)}
                style={{
                    height: '280px',
                    background: '#f8f9fa',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                    padding: '20px',
                    position: 'relative',
                    overflow: 'hidden'
                }}
            >
                <img
                    src={product.image}
                    alt={product.title}
                    style={{
                        maxWidth: '100%',
                        maxHeight: '100%',
                        objectFit: 'contain',
                        transition: 'transform 0.3s ease',
                        transform: isHovered ? 'scale(1.05)' : 'scale(1)'
                    }}
                />
                <div style={{
                    position: 'absolute',
                    top: '12px',
                    right: '12px',
                    background: 'rgba(102, 126, 234, 0.9)',
                    color: 'white',
                    padding: '6px 12px',
                    borderRadius: '20px',
                    fontSize: '11px',
                    fontWeight: '600',
                    textTransform: 'uppercase',
                    letterSpacing: '0.5px'
                }}>
                    {product.category}
                </div>
            </div>

            <div style={{ padding: '20px' }}>
                <h3
                    onClick={() => navigate(`/products/${product.id}`)}
                    style={{
                        fontSize: '16px',
                        fontWeight: '600',
                        color: '#2d3748',
                        marginBottom: '12px',
                        lineHeight: '1.4',
                        height: '44px',
                        overflow: 'hidden',
                        display: '-webkit-box',
                        WebkitLineClamp: 2,
                        WebkitBoxOrient: 'vertical'
                    }}
                >
                    {product.title}
                </h3>

                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '12px' }}>
                    <div style={{
                        fontSize: '24px',
                        fontWeight: '700',
                        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                        WebkitBackgroundClip: 'text',
                        WebkitTextFillColor: 'transparent'
                    }}>
                        ${product.price}
                    </div>
                    <button
                        onClick={() => navigate(`/products/${product.id}`)}
                        style={{
                            padding: '8px 16px',
                            background: 'transparent',
                            color: '#667eea',
                            border: '1px solid #667eea',
                            borderRadius: '8px',
                            fontSize: '13px',
                            fontWeight: '600',
                            cursor: 'pointer'
                        }}
                    >
                        View
                    </button>
                </div>

                <button
                    onClick={handleAddToCart}
                    disabled={orderLoading}
                    style={{
                        width: '100%',
                        padding: '12px',
                        background: orderSuccess
                            ? '#d1fae5'
                            : orderLoading
                                ? '#a0aec0'
                                : 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
                        color: orderSuccess ? '#065f46' : 'white',
                        border: 'none',
                        borderRadius: '10px',
                        fontSize: '14px',
                        fontWeight: '600',
                        cursor: orderLoading ? 'not-allowed' : 'pointer',
                        transition: 'all 0.2s'
                    }}
                >
                    {orderSuccess ? '✅ Added to Orders!' : orderLoading ? 'Adding...' : '🛒 Add to Cart'}
                </button>
            </div>
        </div>
    );
}