import './CommentItem.css'

export default function CommentItem({ comment }) {
    return (
        <div style={{
            background: '#fff',
            padding: '0.8rem',
            borderRadius: '8px',
            boxShadow: '0 2px 6px rgba(0,0,0,0.05)'
        }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '0.3rem' }}>
                <strong>{comment.username}</strong>
                <span style={{ fontSize: '0.85rem', color: '#999' }}>
          {new Date(comment.createdAt).toLocaleString()}
        </span>
            </div>
            <p style={{margin: 0}}>{comment.text}</p>
            <div style={{ marginTop: '0.5rem', display: 'flex', gap: '0.5rem', justifyContent: 'flex-end' }}>
                <button>✏️</button>
                <button>🗑️</button>
            </div>
        </div>
    );
}
