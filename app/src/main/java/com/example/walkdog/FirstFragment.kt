import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.MyAdapter
import com.example.walkdog.Dogs
import com.example.walkdog.R
import com.google.android.gms.maps.model.LatLng

class FirstFragment(var perros: List<Dogs>) : Fragment() {

    lateinit var recyclerView: RecyclerView

    lateinit var btnAddMore: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dogs_view_pager, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        btnAddMore = view.findViewById(R.id.btnAddMore)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = MyAdapter(perros)


        return view
    }
}