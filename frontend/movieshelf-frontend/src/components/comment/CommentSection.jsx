import {useEffect, useState} from 'react';
import {getAllCommentsByMovie, addComment} from '../../api/comment';
import CommentItem from './CommentItem';
import '../../components/comment/CommentSection.css'

export default function CommentSection({imdbId, setCommentsCount}) {
    const [comments, setComments] = useState([]);
    const [text, setText] = useState('');

    useEffect(() => {
        getAllCommentsByMovie(imdbId)
            .then(data => {
                setComments(data);
                setCommentsCount(data.length);
            })
            .catch(console.error);
    }, [imdbId]);

    const handleAdd = () => {
        if (!text.trim()) return;
        addComment(imdbId, text)
            .then(newComment => {
            setComments(prev => [newComment, ...prev]);
            setCommentsCount(prev => prev + 1)
            setText('');
        });
    };

    return (<div className="comments-container">
        <textarea
            value={text}
            onChange={e => {
                setText(e.target.value);
                e.target.rows = 1;
                const lines = e.target.value.split('\n').length + Math.floor(e.target.scrollHeight / 24);
                e.target.rows = Math.min(lines, 10); // ограничим максимумом, если надо
            }}
            placeholder="Write comment..."
            style={{
                resize: 'none',
                overflow: 'hidden',
                width: '100%',
                padding: '0.5rem',
                fontSize: '1rem',
                lineHeight: '1.4',
                borderRadius: '8px',
                border: '1px solid #ccc',
                boxSizing: 'border-box',
                wordBreak: 'break-word',
                whiteSpace: 'pre-wrap',
                transition: 'height 0.2s ease-in-out'
            }}
        />

        <button onClick={handleAdd}>🚀 Add</button>
        <div className="comment-feed">
            {comments.map(c => (<CommentItem key={c.id} comment={c}/>))}
        </div>
    </div>);
}
