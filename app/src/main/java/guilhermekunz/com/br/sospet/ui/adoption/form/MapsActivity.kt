package guilhermekunz.com.br.sospet.ui.adoption.form

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import guilhermekunz.com.br.sospet.R


class MapsActivity : AppCompatActivity() {

    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            val location = LatLng(-28.278148,  -54.270196)
            googleMap.addMarker(MarkerOptions().position(location).title("My Location"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
        })
    }

}

//class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//    var mMap: GoogleMap? = null
//    override fun onMapReady(map: GoogleMap) {
//        mMap = map
//        if (servicesOK() /*&& initMap()*/) {
//
//            //Geocoder gc = new Geocoder(this);
//            //List<Address> list;
//            try {
//                //list = gc.getFromLocationName(hotel.getAddress(), 1);
//                //Address address = list.get(0);
//                val lat = 0.6856979
//                val lng = 16.8795434
//                val latLong = LatLng(lat, lng)
//                //                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLong, 5);
//                Log.i("BEGINNING", "Check this")
//                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLong))
//                Log.i("Finishing", "Check this")
//
//                /*MarkerOptions options = new MarkerOptions()
//                        .title(getString(R.string.landon_hotel) + ", " + city)
//                        .position(new LatLng(lat, lng));
//                mMap.addMarker(options);*/
//                //onMapReady(mMap);
//            } /*catch (IOException e) {
//                Toast.makeText(this, getString(R.string.error_finding_hotel), Toast.LENGTH_SHORT).show();
//            }*/ catch (e: Exception) {
//                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
//                Log.d("Check this->", e.message!!)
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(guilhermekunz.com.br.sospet.R.layout.activity_maps)
//        /*
//        String city = getIntent().getStringExtra("city");
//        setTitle(getString(R.string.landon_hotel) + ", " + city);
//        Hotel hotel = DataProvider.hotelMap.get(city);
//        if (hotel == null) {
//            Toast.makeText(this, getString(R.string.error_find_hotel) + ": "
//                    + city, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        TextView cityText = (TextView) findViewById(R.id.cityText);
//        cityText.setText(hotel.getCity());
//        TextView neighborhoodText = (TextView) findViewById(R.id.neighborhoodText);
//        neighborhoodText.setText(hotel.getNeighborhood());
//        TextView descText = (TextView) findViewById(R.id.descriptionText);
//        descText.setText(hotel.getDescription() + "\n");
//        */
//        val mapFrag = supportFragmentManager
//            .findFragmentById(guilhermekunz.com.br.sospet.R.id.map) as SupportMapFragment?
//        //            mMap = mapFrag.getMap();
//        mapFrag!!.getMapAsync(this)
//        val location = LatLng(13.03, 77.60)
//        mMap?.addMarker(MarkerOptions().position(location).title("My Location"))
//    }
//
//    fun servicesOK(): Boolean {
//        val result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
//        if (result == ConnectionResult.SUCCESS) {
//            return true
//        } else if (GooglePlayServicesUtil.isUserRecoverableError(result)) {
//            val dialog = GooglePlayServicesUtil.getErrorDialog(result, this, DIALOG_REQUEST)
//            dialog!!.show()
//        } else {
//            Toast.makeText(this, "erro", Toast.LENGTH_LONG)
//                .show()
//        }
//        return false
//    } //    private boolean initMap() {
//
//    //        if (mMap == null) {
//    //
//    //        }
//    //        return (mMap != null);
//    //    }
//    companion object {
//        private const val DIALOG_REQUEST = 9001
//    }
//}