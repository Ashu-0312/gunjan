package app.gunjan.utill

interface RecyclerItemClickListener {
    fun onItemClick(parentPos: Int, childPos: Int, data: Any, type: String)
}