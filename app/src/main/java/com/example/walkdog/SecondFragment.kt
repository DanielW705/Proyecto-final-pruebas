import SecondFragmentArgs.*
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.walkdog.Dogs
import com.example.walkdog.PerroViewModel
import com.example.walkdog.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random

class SecondFragment(private val perros: PerroViewModel) : Fragment(), OnMapReadyCallback,
    ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener {

    private lateinit var map: GoogleMap

    private var perroLocation: LatLng? = null

    private val args: SecondFragmentArgs by navArgs()

    companion object {
        fun newInstance(listadePerros: PerroViewModel) = SecondFragment(listadePerros)
    }

    private lateinit var handler: Handler

    private val PerroRunnable = object : Runnable {
        override fun run() {
            DogsMove()
            handler.postDelayed(this, 5000)
        }
    }

    private fun DogsMove() {
        perros.ListDogs.value?.forEach { perro ->
            perro.loc =
                LatLng(
                    perro.loc.latitude + generateRandomOffset(
                        -0.0001,
                        0.0001
                    ), perro.loc.longitude + generateRandomOffset(
                        -0.0001,
                        0.0001
                    )
                )
        }
        map.clear()
        CreateDogsMarker(null)
    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.maps_view_pager, container, false)

        handler = Handler(Looper.getMainLooper())
        createFragment()
//        if (arguments != null) {
//            perroLocation = args.DogLocation
//        }
        return view
    }

    private fun CreateDogsMarker(coordinates: LatLng?) {

        val drawable = context?.let { AppCompatResources.getDrawable(it, R.drawable.ic_dog) }
        val bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable!!.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        if (coordinates != null) {


            perros.ListDogs.value?.forEachIndexed { index, perro ->
                perro.loc =
                    LatLng(
                        coordinates.latitude + (generateRandomOffset(
                            -0.001,
                            0.001
                        ) * (index + 1)),
                        coordinates.longitude + (generateRandomOffset(
                            -0.001,
                            0.001
                        ) * (index + 1))
                    )

                val marker: MarkerOptions =
                    MarkerOptions().position(perro.loc).title(perro.name)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                map.addMarker(marker)
            }
        } else {
            perros.ListDogs.value?.forEach { perro ->
                val marker: MarkerOptions =
                    MarkerOptions().position(perro.loc).title(perro.name)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                map.addMarker(marker)
            }
        }
    }

    private fun createMarker() {

        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener {
                val coordinates = LatLng(it.latitude, it.longitude)
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
                    4000,
                    null
                )
                CreateDogsMarker(coordinates)
            }

        } else {
            map.isMyLocationEnabled = false
            Toast.makeText(
                requireContext(),
                "Ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            )
                .show()
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMyLocationClickListener(this)
        createMarker()
        handler.post(PerroRunnable)
        perros.ListDogs.observe(viewLifecycleOwner, Observer { dogs ->
            updateMap(dogs)
        })
        perroLocation?.let { location ->
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18f))
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }

    override fun onMyLocationClick(p0: Location) {
    }

    private fun generateRandomOffset(min: Double, max: Double): Double {
        return (Random.nextDouble() * (max - min) + min) * (if (Random.nextBoolean()) 1 else -1)
    }

    private fun updateMap(dogs: List<Dogs>) {
        map.clear()
        CreateDogsMarker(null)
    }
}