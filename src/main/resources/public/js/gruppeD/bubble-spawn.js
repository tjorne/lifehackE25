// Bubbles are cool. Bubbles are life #nemoFan

// - Guac

// ___________________________________________________________________________________

function createBubble() {
    const bubble = document.createElement('div');
    bubble.classList.add('bubble');

    const size = Math.random() * 80 + 20;
    bubble.style.width = `${size}px`;
    bubble.style.height = `${size}px`;

    bubble.style.left = `${Math.random() * 100}vw`;
    bubble.style.top = `${Math.random() * 100}vh`;

    bubble.style.background = `rgba(255, 255, 255, ${Math.random() * 0.15 + 0.05})`;

    document.body.appendChild(bubble);

    setTimeout(() => {
        bubble.remove();
    }, 5000);
}

// ___________________________________________________________________________________

setInterval(createBubble, 600);