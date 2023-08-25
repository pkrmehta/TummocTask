package com.pkdev.tummoctask.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.pkdev.tummoctask.R
import com.pkdev.tummoctask.databinding.ActivityMapBinding


class MapActivity : AppCompatActivity(), OnMapReadyCallback, OnClickListener {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapBinding
    private var flag = 0
    private var mapMovedNow = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mapFragment: SupportMapFragment
    private var location: Location?= null
    private val destLocation = arrayListOf(12.9756033,77.6026616) //Cafe Coffee Day
    private val sourceLocation = arrayListOf(13.0209903,77.6383009) //HRBR Layout
    private val transitLocation = arrayListOf(13.0171178,77.6312006) //Kammanahalli Bus Stop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(mapView: GoogleMap) {
        mMap = mapView
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener(this) { location ->
                if (location != null) {
                    this.location = location
                    val mp = MarkerOptions()
                    mp.position(LatLng(location.latitude, location.longitude))
                    mp.title("Your Location")
                    mapView.addMarker(mp)

                    mp.position(LatLng(sourceLocation[0], sourceLocation[1]))
                    mp.title("HRBR Layout")
                    mapView.addMarker(mp)

                    mp.position(LatLng(transitLocation[0], transitLocation[1]))
                    mp.title("CS-St Joseph")
                    mapView.addMarker(mp)

                    mp.position(LatLng(destLocation[0], destLocation[1]))
                    mp.title("Cafe Coffee Day")
                    mapView.addMarker(mp)

                    mapView.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 16f), 1000, null)
                    mapFragment.view?.animate()?.translationY(-400f)?.duration = 500
                    Handler().postDelayed({
                        mapMovedNow = 0
                    },1050)
                }
            }

        mapView.setOnCameraMoveStartedListener {
            onTouch()
        }
        mapView.setOnCameraMoveListener{
            onTouch()
        }
        mapView.setOnMapClickListener {
            onTouch()
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, MapBottomFragment())
        fragmentTransaction.commit()
        binding.fragmentContainer.startAnimation(AnimationUtils.loadAnimation(applicationContext,
            R.anim.slide_up
        ))

        binding.btnBack.setOnClickListener(this)

    }

    fun onMapItemClick(item: String){
        if(flag==0) {
            binding.fragmentContainer.animate().translationY(800f).duration = 800
            when(item){
                "source" -> {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(sourceLocation[0], sourceLocation[1]), 16f), 500, null)
                }
                "destination" -> {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(destLocation[0], destLocation[1]), 16f), 500, null)
                }
                "transit" -> {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(transitLocation[0], transitLocation[1]), 16f), 500, null)
                }
            }
            flag = 1
        }
        else {
            binding.fragmentContainer.animate().translationY(0f).duration = 800
            if(location != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location!!.latitude, location!!.longitude), 16f), 500, null)
                mapFragment.view?.animate()?.translationY(-400f)
            }
            flag = 0
            Handler().postDelayed({
                mapMovedNow = 0
            },550)
        }

    }

    fun onTouch(){
        if(flag==0 && mapMovedNow == 0) {
            binding.fragmentContainer.animate().translationY(800f).duration = 800
            flag = 1
            mapMovedNow = 1
        }
    }

    override fun onClick(clickedView: View?) {
        when(clickedView?.id){
            binding.btnBack.id -> {
                finish()
            }
        }

    }


}