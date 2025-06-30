export default function Home() {
    return (
        <div style={{ textAlign: 'center' }}>
            <h2>Welcome to MovieShelf</h2>
            <p>Jason Statham rules here💥</p>
            <img
                src="/assets/statham.png"
                alt="Jason Statham"
                style={{
                    width: '300px',
                    borderRadius: '8px',
                    boxShadow: '0 0 10px rgba(0,0,0,0.3)',
                    marginTop: '20px'
                }}
            />
        </div>
    );
}