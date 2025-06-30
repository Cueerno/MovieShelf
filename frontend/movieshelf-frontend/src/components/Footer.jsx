export default function Footer() {
    return (
        <footer style={{ padding: '1rem', backgroundColor: '#f1f1f1', textAlign: 'center', marginTop: 'auto' }}>
            <p>© {new Date().getFullYear()} MovieShelf by Vyacheslav Rediuk</p>
        </footer>
    );
}