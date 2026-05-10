document.addEventListener('DOMContentLoaded', () => {
    const container = document.getElementById('courses-container');
    if (!container) {
        return;
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');
    const loggedIn = document.body.dataset.authenticated === 'true';

    async function cargarCursos() {
        try {
            const response = await fetch('/api/courses', {
                headers: csrfToken && csrfHeader ? { [csrfHeader]: csrfToken } : {}
            });

            if (!response.ok) {
                throw new Error('Error al obtener cursos');
            }

            const cursos = await response.json();

            if (!Array.isArray(cursos) || cursos.length === 0) {
                container.innerHTML = '<p>No hay cursos disponibles todavía.</p>';
                return;
            }

            container.innerHTML = cursos.map((curso) => `
                <div class="course-card">
                    <img src="${curso.imageUrl || 'https://via.placeholder.com/320x200?text=Curso'}" alt="${curso.title || 'Curso'}" onerror="this.src='https://via.placeholder.com/320x200?text=Curso'">
                    <div class="course-card-body">
                        <h3>${curso.title || 'Curso sin título'}</h3>
                        <p>${curso.description || 'Sin descripción disponible.'}</p>
                        <a href="${loggedIn ? '/courses/' + curso.id : '/login'}" class="btn-ver-curso">Ver curso</a>
                    </div>
                </div>
            `).join('');
        } catch (error) {
            container.innerHTML = '<p>Error al cargar los cursos.</p>';
            console.error('Error cargando cursos:', error);
        }
    }

    cargarCursos();
});