package com.denis.practice21

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ContentView

class ProductsAdapter(
    context: Context,
    private val layout: Int,
    private val products: List<Product>
): ArrayAdapter<Product?>(
    context,
    layout,
    products
) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(this.layout, parent, false)

        val imgView = view.findViewById<ImageView>(R.id.img)
        val nameView = view.findViewById<TextView>(R.id.name)
        val descrView = view.findViewById<TextView>(R.id.descr)
        val costView = view.findViewById<TextView>(R.id.cost)
        val isOnStock = view.findViewById<TextView>(R.id.isOnStock)

        val product = products[position]

        imgView.setImageResource(product.imgResource)
        nameView.text = product.name
        descrView.text = product.description
        costView.text = "${product.cost}₽"
        isOnStock.text = if(product.isOnStock) "В наличии" else "Не в наличии"

        return view
    }

}