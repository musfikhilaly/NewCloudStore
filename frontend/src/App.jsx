import { useState, useEffect } from 'react'
import './App.css'

/**
 * App - Main React component for CloudStore frontend.
 *
 * For Day 1, this component:
 * 1. Shows a welcome header
 * 2. Fetches products from our product-service (port 8081)
 * 3. Displays them in a simple list
 *
 * useEffect = runs code when the component loads (like a page load event)
 * useState  = stores data that can change (like products list)
 * fetch()   = makes HTTP requests (like calling our API)
 */
function App() {
  // State to store the list of products
  const [products, setProducts] = useState([])
  // State to track if we're still loading
  const [loading, setLoading] = useState(true)
  // State to track any errors
  const [error, setError] = useState(null)

  // useEffect runs once when the component first loads
  useEffect(() => {
    // Fetch products from our Product Service
    fetch('http://localhost:8081/products')
      .then(response => {
        if (!response.ok) {
          throw new Error('Product service not running! Start it in IntelliJ first.')
        }
        return response.json()  // Convert response to JavaScript object
      })
      .then(data => {
        setProducts(data)       // Save products to state
        setLoading(false)       // Done loading
      })
      .catch(err => {
        setError(err.message)   // Save error message
        setLoading(false)       // Done loading (even though it failed)
      })
  }, [])  // Empty array = run only once when component mounts

  return (
    <div style={{ fontFamily: 'Arial, sans-serif', maxWidth: '900px', margin: '0 auto', padding: '20px' }}>

      {/* Header */}
      <h1 style={{ color: '#2563eb', borderBottom: '2px solid #2563eb', paddingBottom: '10px' }}>
        ☁️ CloudStore
      </h1>
      <p style={{ color: '#666' }}>
        Cloud-based Java Microservices | Day 1 Foundation
      </p>

      {/* Service Status */}
      <div style={{ background: '#f0f9ff', border: '1px solid #bae6fd', borderRadius: '8px', padding: '15px', margin: '20px 0' }}>
        <h3 style={{ margin: '0 0 10px 0', color: '#0369a1' }}>🔌 Service Status</h3>
        <p>Product Service: <a href="http://localhost:8081/products" target="_blank" rel="noreferrer">http://localhost:8081/products</a></p>
        <p>User/Order Service: <a href="http://localhost:8082/health" target="_blank" rel="noreferrer">http://localhost:8082/health</a></p>
      </div>

      {/* Products Section */}
      <h2>🛍️ Products (from FakeStore API via Product Service)</h2>

      {/* Loading state */}
      {loading && (
        <p style={{ color: '#666', fontStyle: 'italic' }}>
          ⏳ Loading products from product-service on port 8081...
          <br/>
          <small>(Make sure product-service is running in IntelliJ!)</small>
        </p>
      )}

      {/* Error state */}
      {error && (
        <div style={{ background: '#fef2f2', border: '1px solid #fecaca', borderRadius: '8px', padding: '15px', color: '#dc2626' }}>
          <strong>❌ Error:</strong> {error}
          <br/>
          <small>Make sure product-service is running in IntelliJ on port 8081.</small>
        </div>
      )}

      {/* Products list */}
      {!loading && !error && (
        <div>
          <p style={{ color: '#16a34a' }}>✅ Successfully loaded {products.length} products!</p>
          <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(250px, 1fr))', gap: '15px' }}>
            {products.slice(0, 8).map(product => (  // Show first 8 products
              <div key={product.id} style={{
                border: '1px solid #e5e7eb',
                borderRadius: '8px',
                padding: '15px',
                background: 'white',
                boxShadow: '0 1px 3px rgba(0,0,0,0.1)'
              }}>
                <img
                  src={product.image}
                  alt={product.title}
                  style={{ width: '80px', height: '80px', objectFit: 'contain', display: 'block', margin: '0 auto 10px' }}
                />
                <h4 style={{ fontSize: '13px', color: '#374151', margin: '0 0 8px 0', lineHeight: '1.4' }}>
                  {product.title.length > 50 ? product.title.substring(0, 50) + '...' : product.title}
                </h4>
                <p style={{ color: '#2563eb', fontWeight: 'bold', fontSize: '16px', margin: '0' }}>
                  ${product.price}
                </p>
                <span style={{
                  display: 'inline-block',
                  background: '#f3f4f6',
                  color: '#6b7280',
                  fontSize: '11px',
                  padding: '2px 8px',
                  borderRadius: '999px',
                  marginTop: '8px'
                }}>
                  {product.category}
                </span>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  )
}

export default App