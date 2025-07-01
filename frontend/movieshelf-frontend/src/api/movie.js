const API_BASE = 'http://localhost:8080/api/v1/movies';

export async function movieByImdbId(imdbId) {
    try {
        console.log(imdbId)
        const res = await fetch(`${API_BASE}/${imdbId}`);

        if (!res.ok) {
            throw new Error('Ошибка загрузки фильма');
        }

        const data = await res.json();
        return data;

    } catch (err) {
        throw err;
    }
}
