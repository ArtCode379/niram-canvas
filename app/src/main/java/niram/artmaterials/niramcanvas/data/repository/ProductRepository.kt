package niram.artmaterials.niramcanvas.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import niram.artmaterials.niramcanvas.data.model.Product
import niram.artmaterials.niramcanvas.data.model.ProductCategory

class ProductRepository {
    private val products = listOf(
        Product(
            1,
            "Artist Acrylic Set",
            "A balanced set of 24 richly pigmented acrylic colours for canvas, board, and mixed media.",
            ProductCategory.PAINTS,
            29.90,
            "https://images.unsplash.com/photo-1549490349-8643362247b5?w=1200",
        ),
        Product(
            2,
            "Watercolour Pocket Box",
            "Twelve transparent watercolours in a travel-ready metal palette with mixing wells and a compact brush.",
            ProductCategory.PAINTS,
            18.50,
            "https://images.unsplash.com/photo-1513364776144-60967b0f800f?w=1200",
        ),
        Product(
            3,
            "Studio Brush Collection",
            "Ten synthetic brushes in round, flat, filbert, and detail shapes for acrylic and watercolour.",
            ProductCategory.BRUSHES,
            22.00,
            "https://images.unsplash.com/photo-1561839561-b13bcfe95249?w=1200",
        ),
        Product(
            4,
            "Cotton Canvas 50 × 70",
            "Triple-primed, medium-grain cotton canvas stretched by hand on sturdy kiln-dried pine bars.",
            ProductCategory.CANVASES,
            16.75,
            "https://images.unsplash.com/photo-1577083552431-6e5fd01aa342?w=1200",
        ),
        Product(
            5,
            "A4 Watercolour Pad",
            "Twenty sheets of acid-free, cold-pressed 300 gsm paper for washes, ink, and gouache.",
            ProductCategory.PAPER,
            12.40,
            "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=1200",
        ),
        Product(
            6,
            "Graphite Sketching Kit",
            "Twelve graphite grades, blending stump, kneaded eraser, and sharpener in a complete drawing kit.",
            ProductCategory.DRAWING,
            14.90,
            "https://images.unsplash.com/photo-1519682337058-a94d519337bc?w=1200",
        ),
        Product(
            7,
            "Soft Pastel Landscape Set",
            "Thirty-six velvety pastels selected for skies, foliage, earth, and luminous landscape highlights.",
            ProductCategory.DRAWING,
            26.80,
            "https://images.unsplash.com/photo-1455390582262-044cdead277a?w=1200",
        ),
        Product(
            8,
            "Linen Canvas Panel Pack",
            "Six archival linen-texture panels with rigid backing, ideal for studies and plein-air work.",
            ProductCategory.CANVASES,
            21.60,
            "https://images.unsplash.com/photo-1541961017774-22349e4a1262?w=1200",
        ),
        Product(
            9,
            "Fine Liner Pen Set",
            "Eight waterproof pigment liners from 0.05 to 0.8 mm for illustration and technical detail.",
            ProductCategory.DRAWING,
            11.95,
            "https://images.unsplash.com/photo-1456324504439-367cee3b3c32?w=1200",
        ),
        Product(
            10,
            "Natural Clay Starter Kit",
            "Air-dry clay, wooden modelling tools, sponge, roller, and a beginner project guide.",
            ProductCategory.CRAFT,
            24.50,
            "https://images.unsplash.com/photo-1610701596007-11502861dcfa?w=1200",
        ),
        Product(
            11,
            "Metallic Leaf Craft Set",
            "Gold, copper, and silver imitation leaf with adhesive and sealer for decorative projects.",
            ProductCategory.CRAFT,
            19.25,
            "https://images.unsplash.com/photo-1607344645866-009c7dabeccb?w=1200",
        ),
        Product(
            12,
            "Mixed Media Sketchbook",
            "A lay-flat A5 sketchbook with heavyweight pages for pencil, marker, collage, ink, and light washes.",
            ProductCategory.PAPER,
            15.30,
            "https://images.unsplash.com/photo-1531346680769-a1d79b57de5c?w=1200",
        ),
    )

    fun observeById(id: Int): Flow<Product?> = flowOf(products.find { it.id == id })

    fun getById(id: Int): Product? = products.find { it.id == id }

    fun observeAll(): Flow<List<Product>> = flowOf(products)
}
