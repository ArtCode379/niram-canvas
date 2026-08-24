package niram.artmaterials.niramcanvas.data.model

import androidx.annotation.StringRes
import niram.artmaterials.niramcanvas.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    PAINTS(R.string.iwurv_category_paints),
    BRUSHES(R.string.iwurv_category_brushes),
    CANVASES(R.string.iwurv_category_canvases),
    PAPER(R.string.iwurv_category_paper),
    DRAWING(R.string.iwurv_category_drawing),
    CRAFT(R.string.iwurv_category_craft),
}
