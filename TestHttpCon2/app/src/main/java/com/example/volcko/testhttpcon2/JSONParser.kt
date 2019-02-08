package com.example.volcko.testhttpcon

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.WorkSource
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import com.example.volcko.testhttpcon2.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

@Suppress("DEPRECATION")
class JSONParser(private var c: Context, private var jsonData: String, private var myGridView: GridView) : AsyncTask<Void, Void, Boolean> () {

    private lateinit var pd: ProgressDialog
    private var recipes = ArrayList<Recipe>()

    class Recipe(private var id: String, private var name: String, private var category: String, private var area: String, private var instructions: String, private var image: String, private var tags: String, private var ingredients: String, private var measures: String, private var video: String, private var source: String) {

        fun getId(): String {
            return id
        }

        fun getName(): String {
            return name
        }

        fun getCategory(): String {
            return category
        }

        fun getArea(): String {
            return area
        }

        fun getInstructions(): String {
            return instructions
        }

        fun getImage(): String {
            return image
        }

        fun getTags(): String {
            return tags
        }

        fun getIngredients(): String {
            return ingredients
        }

        fun getMeasures(): String {
            return measures
        }

        fun getVideo(): String {
            return video
        }

        fun getSource(): String {
            return source
        }

    }

    class MrAdapter(private var c: Context, private var recipes: ArrayList<Recipe>) : BaseAdapter() {

        override fun getCount(): Int {
            return recipes.size
        }

        override fun getItem(pos: Int): Any {
            return recipes[pos]
        }

        override fun getItemId(pos: Int): Long {
            return pos.toLong()
        }

        // inflate row_model.xml and return it
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var convertView: View? = view
            if (convertView == null) {
                convertView = LayoutInflater.from(c).inflate(R.layout.recipe_view, viewGroup, false)
            }

            val categoryTxt = convertView!!.findViewById<TextView>(R.id.recipe_label) as TextView
            val nameTxt = convertView.findViewById<TextView>(R.id.recipe_name) as TextView
            val reviewTxt = convertView.findViewById<TextView>(R.id.recipe_num_review) as TextView

            val recipe = this.getItem(i) as Recipe

            categoryTxt.text = recipe.getCategory()
            nameTxt.text = recipe.getName()
            reviewTxt.text = recipe.getId()

            convertView.setOnClickListener ({ Toast.makeText(c, recipe.getName(), Toast.LENGTH_SHORT).show() })

            return convertView
        }

    }

     //parse JSON data
    private fun parse(): Boolean {
         try {
             val ja = JSONArray(jsonData)
             var jo: JSONObject

             recipes.clear()
             var recipe: Recipe

             for (i in 0 until ja.length()) {
                 jo = ja.getJSONObject(i)

                 val name = jo.getString("meal")
                 val category = jo.getString("category")
                 // actualy id = review
                 val review = jo.getString("id")
                 val area = jo.getString("area")
                 val instructions = jo.getString("instructions")
                 val image = jo.getString("image")
                 val tags = jo.getString("tags")
                 val ingredients = jo.getString("ingredients")
                 val measures = jo.getString("measures")
                 val video = jo.getString("video")
                 val source = jo.getString("source")

                 recipe = Recipe(review, name, category, area, instructions, image, tags, ingredients, measures, video, source)
                 recipes.add(recipe)
             }

             return true
         } catch (e: JSONException) {
             e.printStackTrace()
             return false
         }
     }

    override fun onPreExecute() {
        super.onPreExecute()

        pd = ProgressDialog(c)
        pd.setTitle("Parse JSON")
        pd.setMessage("Parsing...Please wait")
        pd.show()
    }

    override fun doInBackground(vararg voids: Void): Boolean? {
        return parse()
    }

    override fun onPostExecute(isParsed: Boolean?) {
        super.onPostExecute(isParsed)

        pd.dismiss()
        if (isParsed!!) {
            // bind
            myGridView.adapter = MrAdapter(c, recipes)
        }else {
            Toast.makeText(c, "Unable To Parse that data. ARE YOU SURE IT IS VALID JSON DATA? JsonException was raised. Check Log Output.", Toast.LENGTH_SHORT).show()
            Toast.makeText(c, "THIS IS THE DATA WE WERE TRYING TO PARSE :  " + jsonData, Toast.LENGTH_LONG).show()
        }
    }

}