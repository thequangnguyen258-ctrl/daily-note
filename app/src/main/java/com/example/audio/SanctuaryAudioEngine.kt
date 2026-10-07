package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.*
import kotlin.math.*

class SanctuaryAudioEngine {

    private val sampleRate = 44100
    private var isPlaying = false
    private var currentTrack = "miracle_528"
    private var volume = 0.6f
    private var isMuted = false
    private var audioTrack: AudioTrack? = null
    private var renderJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    fun setMuted(muted: Boolean) {
        isMuted = muted
        updateVolume()
    }

    fun isMuted(): Boolean = isMuted

    fun setVolume(vol: Float) {
        volume = vol.coerceIn(0f, 1f)
        updateVolume()
    }

    fun getVolume(): Float = volume

    fun getCurrentTrack(): String = currentTrack

    fun isPlayingAudio(): Boolean = isPlaying

    private fun updateVolume() {
        val finalVol = if (isMuted) 0f else volume
        try {
            audioTrack?.setVolume(finalVol)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playAmbient(trackId: String) {
        stopAmbient()
        currentTrack = trackId
        isPlaying = true

        val bufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ) * 2

        audioTrack = AudioTrack.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            .setAudioFormat(
                AudioFormat.Builder()
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(bufferSize)
            .setTransferMode(AudioTrack.MODE_STREAM)
            .build()

        updateVolume()
        audioTrack?.play()

        renderJob = scope.launch {
            val chunk = ShortArray(1024)
            var phase1 = 0.0
            var phase2 = 0.0
            var phase3 = 0.0
            var t = 0L

            while (isActive && isPlaying) {
                for (i in chunk.indices) {
                    val sample = when (currentTrack) {
                        "miracle_528" -> {
                            // 528 Hz Solfeggio pure harmonic sine + 264 Hz sub-octave
                            val freq1 = 528.0
                            val freq2 = 264.0
                            val amp1 = sin(phase1) * 0.4
                            val amp2 = sin(phase2) * 0.15
                            val lfo = (sin(t * 0.00008) + 1.0) * 0.5 * 0.2 + 0.8
                            phase1 += 2.0 * Math.PI * freq1 / sampleRate
                            phase2 += 2.0 * Math.PI * freq2 / sampleRate
                            (amp1 + amp2) * lfo
                        }
                        "living_water" -> {
                            // Organic water modulation
                            val waterLfo = sin(t * 0.00015) * 80.0
                            val centerFreq = 420.0 + waterLfo
                            val ripple = sin(phase1) * 0.25 + sin(phase2) * 0.15
                            phase1 += 2.0 * Math.PI * centerFreq / sampleRate
                            phase2 += 2.0 * Math.PI * (centerFreq * 1.5) / sampleRate
                            ripple
                        }
                        "wind_chimes" -> {
                            // Celestial chimes with pentatonic frequencies
                            val chimeFreq = 528.0 + (sin(t * 0.00005) * 200.0)
                            val chime = sin(phase1) * 0.35 * exp(-((t % 22050) / 22050.0) * 3.0)
                            phase1 += 2.0 * Math.PI * chimeFreq / sampleRate
                            chime
                        }
                        "deep_night" -> {
                            // 432 Hz healing alpha wave tone
                            val freq1 = 432.0
                            val freq2 = 216.0
                            val amp1 = sin(phase1) * 0.35
                            val amp2 = sin(phase2) * 0.2
                            phase1 += 2.0 * Math.PI * freq1 / sampleRate
                            phase2 += 2.0 * Math.PI * freq2 / sampleRate
                            amp1 + amp2
                        }
                        else -> {
                            val freq = 528.0
                            val amp = sin(phase1) * 0.3
                            phase1 += 2.0 * Math.PI * freq / sampleRate
                            amp
                        }
                    }
                    chunk[i] = (sample * 32767.0 * 0.6).toInt().coerceIn(-32768, 32767).toShort()
                    t++
                }
                audioTrack?.write(chunk, 0, chunk.size)
            }
        }
    }

    fun stopAmbient() {
        isPlaying = false
        renderJob?.cancel()
        renderJob = null
        try {
            audioTrack?.stop()
            audioTrack?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        audioTrack = null
    }

    fun toggleAmbient(trackId: String = currentTrack): Boolean {
        return if (isPlaying && currentTrack == trackId) {
            stopAmbient()
            false
        } else {
            playAmbient(trackId)
            true
        }
    }

    fun playBellChime(freq: Float = 528f) {
        if (isMuted) return
        scope.launch {
            try {
                val durationMs = 1200
                val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
                val buffer = ShortArray(numSamples)
                var phase = 0.0

                for (i in 0 until numSamples) {
                    val progress = i.toDouble() / numSamples
                    val envelope = exp(-progress * 4.5)
                    val sample = sin(phase) * envelope
                    buffer[i] = (sample * 32767.0 * volume * 0.5).toInt().coerceIn(-32768, 32767).toShort()
                    phase += 2.0 * Math.PI * freq / sampleRate
                }

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, buffer.size)
                track.play()
                delay(durationMs.toLong())
                track.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun playWaterDrop() {
        if (isMuted) return
        scope.launch {
            try {
                val durationMs = 300
                val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
                val buffer = ShortArray(numSamples)
                var phase = 0.0

                for (i in 0 until numSamples) {
                    val progress = i.toDouble() / numSamples
                    val sweepFreq = 450.0 + progress * 950.0
                    val envelope = exp(-progress * 6.0)
                    val sample = sin(phase) * envelope
                    buffer[i] = (sample * 32767.0 * volume * 0.45).toInt().coerceIn(-32768, 32767).toShort()
                    phase += 2.0 * Math.PI * sweepFreq / sampleRate
                }

                val track = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(buffer.size * 2)
                    .setTransferMode(AudioTrack.MODE_STATIC)
                    .build()

                track.write(buffer, 0, buffer.size)
                track.play()
                delay(durationMs.toLong())
                track.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun playTouchChime(index: Int = 0) {
        val notes = floatArrayOf(261.63f, 293.66f, 329.63f, 392.00f, 440.00f, 523.25f, 587.33f, 659.25f, 783.99f)
        val note = notes[index.coerceIn(0, notes.size - 1)]
        playBellChime(note)
    }

    fun release() {
        stopAmbient()
        scope.cancel()
    }
}
