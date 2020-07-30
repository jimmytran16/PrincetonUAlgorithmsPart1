// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioFileFormat;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Line;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

public final class StdAudio
{
    public static final int SAMPLE_RATE = 44100;
    private static final int BYTES_PER_SAMPLE = 2;
    private static final int BITS_PER_SAMPLE = 16;
    private static final double MAX_16_BIT = 32768.0;
    private static final int SAMPLE_BUFFER_SIZE = 4096;
    private static final int MONO = 1;
    private static final int STEREO = 2;
    private static final boolean LITTLE_ENDIAN = false;
    private static final boolean BIG_ENDIAN = true;
    private static final boolean SIGNED = true;
    private static final boolean UNSIGNED = false;
    private static SourceDataLine line;
    private static byte[] buffer;
    private static int bufferSize;
    
    private StdAudio() {
    }
    
    private static void init() {
        try {
            final AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, false);
            final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            (StdAudio.line = (SourceDataLine)AudioSystem.getLine(info)).open(format, 8192);
            StdAudio.buffer = new byte[2730];
        }
        catch (LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
        StdAudio.line.start();
    }
    
    private static AudioInputStream getAudioInputStreamFromFile(final String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("filename is null");
        }
        try {
            final File file = new File(filename);
            if (file.exists()) {
                return AudioSystem.getAudioInputStream(file);
            }
            final InputStream is1 = StdAudio.class.getResourceAsStream(filename);
            if (is1 != null) {
                return AudioSystem.getAudioInputStream(is1);
            }
            final InputStream is2 = StdAudio.class.getClassLoader().getResourceAsStream(filename);
            if (is2 != null) {
                return AudioSystem.getAudioInputStream(is2);
            }
            throw new IllegalArgumentException("could not read '" + filename + "'");
        }
        catch (IOException e) {
            throw new IllegalArgumentException("could not read '" + filename + "'", e);
        }
        catch (UnsupportedAudioFileException e2) {
            throw new IllegalArgumentException("file of unsupported audio format: '" + filename + "'", e2);
        }
    }
    
    public static void close() {
        StdAudio.line.drain();
        StdAudio.line.stop();
    }
    
    public static void play(double sample) {
        if (Double.isNaN(sample)) {
            throw new IllegalArgumentException("sample is NaN");
        }
        if (sample < -1.0) {
            sample = -1.0;
        }
        if (sample > 1.0) {
            sample = 1.0;
        }
        short s = (short)(32768.0 * sample);
        if (sample == 1.0) {
            s = 32767;
        }
        StdAudio.buffer[StdAudio.bufferSize++] = (byte)s;
        StdAudio.buffer[StdAudio.bufferSize++] = (byte)(s >> 8);
        if (StdAudio.bufferSize >= StdAudio.buffer.length) {
            StdAudio.line.write(StdAudio.buffer, 0, StdAudio.buffer.length);
            StdAudio.bufferSize = 0;
        }
    }
    
    public static void play(final double[] samples) {
        if (samples == null) {
            throw new IllegalArgumentException("argument to play() is null");
        }
        for (int i = 0; i < samples.length; ++i) {
            play(samples[i]);
        }
    }
    
    public static double[] read(final String filename) {
        final AudioInputStream ais = getAudioInputStreamFromFile(filename);
        final AudioFormat audioFormat = ais.getFormat();
        if (audioFormat.getSampleRate() != 44100.0f) {
            throw new IllegalArgumentException("StdAudio.read() currently supports only a sample rate of 44100 Hz\naudio format: " + audioFormat);
        }
        if (audioFormat.getSampleSizeInBits() != 16) {
            throw new IllegalArgumentException("StdAudio.read() currently supports only 16-bit audio\naudio format: " + audioFormat);
        }
        if (audioFormat.isBigEndian()) {
            throw new IllegalArgumentException("StdAudio.read() currently supports only audio stored using little endian\naudio format: " + audioFormat);
        }
        byte[] bytes = null;
        try {
            final int bytesToRead = ais.available();
            bytes = new byte[bytesToRead];
            final int bytesRead = ais.read(bytes);
            if (bytesToRead != bytesRead) {
                throw new IllegalStateException("read only " + bytesRead + " of " + bytesToRead + " bytes");
            }
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("could not read '" + filename + "'", ioe);
        }
        final int n = bytes.length;
        if (audioFormat.getChannels() == 1) {
            final double[] data = new double[n / 2];
            for (int i = 0; i < n / 2; ++i) {
                data[i] = (short)((bytes[2 * i + 1] & 0xFF) << 8 | (bytes[2 * i] & 0xFF)) / 32768.0;
            }
            return data;
        }
        if (audioFormat.getChannels() == 2) {
            final double[] data = new double[n / 4];
            for (int i = 0; i < n / 4; ++i) {
                final double left = (short)((bytes[4 * i + 1] & 0xFF) << 8 | (bytes[4 * i + 0] & 0xFF)) / 32768.0;
                final double right = (short)((bytes[4 * i + 3] & 0xFF) << 8 | (bytes[4 * i + 2] & 0xFF)) / 32768.0;
                data[i] = (left + right) / 2.0;
            }
            return data;
        }
        throw new IllegalStateException("audio format is neither mono or stereo");
    }
    
    public static void save(final String filename, final double[] samples) {
        if (filename == null) {
            throw new IllegalArgumentException("filenameis null");
        }
        if (samples == null) {
            throw new IllegalArgumentException("samples[] is null");
        }
        final AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, false);
        final byte[] data = new byte[2 * samples.length];
        for (int i = 0; i < samples.length; ++i) {
            int temp = (short)(samples[i] * 32768.0);
            if (samples[i] == 1.0) {
                temp = 32767;
            }
            data[2 * i + 0] = (byte)temp;
            data[2 * i + 1] = (byte)(temp >> 8);
        }
        try {
            final ByteArrayInputStream bais = new ByteArrayInputStream(data);
            final AudioInputStream ais = new AudioInputStream(bais, format, samples.length);
            if (filename.endsWith(".wav") || filename.endsWith(".WAV")) {
                AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File(filename));
            }
            else {
                if (!filename.endsWith(".au") && !filename.endsWith(".AU")) {
                    throw new IllegalArgumentException("file type for saving must be .wav or .au");
                }
                AudioSystem.write(ais, AudioFileFormat.Type.AU, new File(filename));
            }
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("unable to save file '" + filename + "'", ioe);
        }
    }
    
    public static synchronized void play(final String filename) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AudioInputStream ais = getAudioInputStreamFromFile(filename);
                stream(ais);
            }
        }).start();
    }
    
    private static void stream(final AudioInputStream ais) {
        SourceDataLine line = null;
        final int BUFFER_SIZE = 4096;
        try {
            final AudioFormat audioFormat = ais.getFormat();
            final DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            line = (SourceDataLine)AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();
            final byte[] samples = new byte[BUFFER_SIZE];
            int count = 0;
            while ((count = ais.read(samples, 0, BUFFER_SIZE)) != -1) {
                line.write(samples, 0, count);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (LineUnavailableException e2) {
            e2.printStackTrace();
        }
        finally {
            if (line != null) {
                line.drain();
                line.close();
            }
        }
    }
    
    public static synchronized void loop(final String filename) {
        if (filename == null) {
            throw new IllegalArgumentException();
        }
        final AudioInputStream ais = getAudioInputStreamFromFile(filename);
        try {
            final Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.loop(-1);
        }
        catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        while (true) {
                            Thread.sleep(1000L);
                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }
                    break;
                }
            }
        }).start();
    }
    
    private static double[] note(final double hz, final double duration, final double amplitude) {
        final int n = (int)(44100.0 * duration);
        final double[] a = new double[n + 1];
        for (int i = 0; i <= n; ++i) {
            a[i] = amplitude * Math.sin(6.283185307179586 * i * hz / 44100.0);
        }
        return a;
    }
    
    public static void main(final String[] args) {
        final double freq = 440.0;
        for (int i = 0; i <= 44100; ++i) {
            play(0.5 * Math.sin(6.283185307179586 * freq * i / 44100.0));
        }
        final int[] steps = { 0, 2, 4, 5, 7, 9, 11, 12 };
        for (int j = 0; j < steps.length; ++j) {
            final double hz = 440.0 * Math.pow(2.0, steps[j] / 12.0);
            play(note(hz, 1.0, 0.5));
        }
        close();
    }
    
    static {
        StdAudio.bufferSize = 0;
        init();
    }
}
