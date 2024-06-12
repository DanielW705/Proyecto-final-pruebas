import FirstFragmentDirections.*
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.MyAdapter
import com.example.walkdog.Dogs
import com.example.walkdog.PerroViewModel
import com.example.walkdog.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlin.random.Random

class FirstFragment(private val perros: PerroViewModel) : Fragment() {

    lateinit var recyclerView: RecyclerView

    lateinit var btnAddMore: Button

    lateinit var adapter: MyAdapter

    private lateinit var navController: NavController

    companion object {
        fun newInstance(listadePerros: PerroViewModel) = SecondFragment(listadePerros)
    }

    private fun showAddDogDialog() {
        var builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Agregar mascota")
        val input = EditText(requireContext())
        input.hint = "Nombre de su mascota"
        builder.setView(input)

        builder.setPositiveButton("Ok") { dialog, _ ->
            val newPerroNombre = input.text.toString()

            if (newPerroNombre.isNotEmpty() && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val fusedLocationClient: FusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    val coordinates = LatLng(it.latitude, it.longitude)
                    val newDog = Dogs(
                        newPerroNombre, LatLng(
                            coordinates.latitude + generateRandomOffset(-0.0001, 0.0001),
                            coordinates.longitude + generateRandomOffset(-0.0001, 0.0001)
                        )
                    )
                    perros.addDog(newDog)
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "La ubicacion no esta permitida, o el nombre del perro no puede estar vacio",
                    Toast.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun generateRandomOffset(min: Double, max: Double): Double {
        return (Random.nextDouble() * (max - min) + min) * (if (Random.nextBoolean()) 1 else -1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dogs_view_pager, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        btnAddMore = view.findViewById(R.id.btnAddMore)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())


//        adapter = MyAdapter(perros.ListDogs.value ?: mutableListOf()) { perro ->
//            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(perro.loc)
//            findNavController().navigate(action)
//        }
        adapter = MyAdapter(perros.ListDogs.value ?: mutableListOf())
        recyclerView.adapter = adapter

        btnAddMore.setOnClickListener {
            showAddDogDialog()
        }
        perros.ListDogs.observe(viewLifecycleOwner, Observer { newDogs ->
            adapter.updateData(newDogs)
        })
        return view
    }


}