document.addEventListener('DOMContentLoaded', () => {

    const minWidth = 768;
    const screenWidth = window.innerWidth;
    const ua = navigator.userAgent;
    const isIpad = /iPad|Macintosh/.test(ua) && 'ontouchend' in document;

    if (screenWidth < minWidth && !isIpad) {
        document.body.innerHTML = `
            <div style="
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
                text-align: center;
                background-color: #111;
                color: #fff;
                font-family: sans-serif;
                padding: 2rem;
            ">
                <h1>Woa - Hold on!</h1>
                <p>BeyondBorders is not available on small screens (yet).</p>
                <p>Please use a tablet or desktop for full access for now.</p>
            </div>
        `;
        return;
    }

});