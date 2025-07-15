import {useEffect, useState} from 'react';
import {getAllCommentsByMovie, addComment} from '../../api/comment';
import CommentItem from './CommentItem';
import '../../components/comment/CommentSection.css'

export default function CommentSection({imdbId}) {
    const [comments, setComments] = useState([]);
    const [text, setText] = useState('');

    useEffect(() => {
        getAllCommentsByMovie(imdbId)
            .then(setComments)
            .catch(console.error);
    }, [imdbId]);

    const handleAdd = () => {
        if (!text.trim()) return;
        addComment(imdbId, text).then(newComment => {
            setComments(prev => [newComment, ...prev]);
            setText('');
        });
    };

    return (<div className="comments-container">
        <textarea
            value={text}
            onChange={e => setText(e.target.value)}
            placeholder="Write comment..."
            rows={2}
        />
        <button onClick={handleAdd}>🚀 Add</button>
        <div className="comment-feed">
            {comments.map(c => (<CommentItem key={c.id} comment={c}/>))}
        </div>
    </div>);
}
