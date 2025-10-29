package com.se310.store.model;

import java.util.Map;
/**
 * Appliance class implementation representing Appliance Device in the Store
 *
 * @author  Sergey L. Sundukovskiy
 * @version 1.0
 * @since   2025-09-25
 */
public class Appliance extends Device {
    //TODO: Implement Observer notification related to event processing
    //TODO: Implement Observer notification related to command processing

    /**
     * Constructor for Appliance class
     * @param id
     * @param name
     * @param storeLocation
     * @param type
     */
    public Appliance(String id, String name, StoreLocation storeLocation, String type) {
        super(id, name, storeLocation, type);
        initHandlers();
    }

    /**
     * Appliance specific event processing
     * Notifies observers when an event is processed
     */
    @Override
    public void processEvent(String event) {
        System.out.println("Processing Event : " + event);
        emitEvent(event);
    }

    /**
     * This is a placeholder for the processing commands
     * Notifies observers when a command is issued
     * @param command
     */
    // public void processCommand(String command){
    //     System.out.println("<<< " + "Processing Command : " + command);
    //     try {
    //         // ... do command ...
    //         emitEvent("CMD " + command + " status=OK");
    //     } catch (Exception ex) {
    //         emitAlert("CMD " + command + " status=FAIL error=" + ex.getMessage());
    //         throw ex;
    //     }      
    // }

    public void processCommand(String command) {
        Parsed pc;
        try { pc = parse(command); }
        catch (Exception e) { emitAlert("CMD parse_fail reason=" + e.getMessage()); return; }

        CommandHandler h = handlers.get(pc.verb);
        if (h == null) {
            emitAlert("CMD " + pc.verb + " status=FAIL reason=UNKNOWN_VERB");
            return;
        }
        try {
            h.handle(pc); 
            emitEvent("CMD " + pc.verb + " params=" + pc.params);
        } catch (Exception ex) {
            emitAlert("CMD " + pc.verb + " error=" + ex.getMessage());
        }
    }


    private interface CommandHandler { void handle(Parsed pc); }
    private final Map<String, CommandHandler> handlers = new java.util.HashMap<>();

    private void initHandlers() {
        String t = (getType() == null ? "" : getType().toUpperCase());
        switch (t) {
            case "TURNSTILE" -> new TurnstileHandlers().register();
            case "ROBOT"     -> new RobotHandlers().register();
            case "SPEAKER"   -> new SpeakerHandlers().register();
        }
    }

    // SHENANIGANS
    private static class Parsed {
        final String verb;
        final Map<String, String> params;
        Parsed(String verb, Map<String,String> params){ this.verb=verb; this.params=params; }
    }
    private Parsed parse(String command) {
        if (command == null || command.isBlank())
            throw new IllegalArgumentException("Empty command");
        String[] parts = command.trim().split("\\s+");
        String verb = parts[0].toUpperCase();
        Map<String,String> params = new java.util.HashMap<>();
        for (int i=1; i<parts.length; i++) {
            String p = parts[i];
            int eq = p.indexOf('=');
            if (eq > 0) params.put(p.substring(0,eq), p.substring(eq+1));
            else params.put("arg"+i, p); // positional fallback
        }
        return new Parsed(verb, params);
    }

    private class TurnstileHandlers {
        void register() {
            handlers.put("LOCK",   pc -> turnstileLock());
            handlers.put("UNLOCK", pc -> turnstileUnlock());
            handlers.put("OPEN",   pc -> turnstileOpen());
            handlers.put("CLOSE",  pc -> turnstileClose());
        }
        void turnstileLock()  {/* ... */ }
        void turnstileUnlock(){ /* ... */ }
        void turnstileOpen()  { /* ... */ }
        void turnstileClose() { /* ... */ }
    }

    private class RobotHandlers {
        void register() {
            handlers.put("START",   pc -> robotStart());
            handlers.put("STOP",    pc -> robotStop());
            handlers.put("MOVE_TO", pc -> robotMoveTo(0,0));
        }
        void robotStart(){}
        void robotStop(){}
        void robotMoveTo(int x, int y){ /* ... */ }
    }

    private class SpeakerHandlers {
        void register() {
            handlers.put("PLAY", pc -> {
                speakerPlay("I am losing my mind", "mockTrack");
            });
            handlers.put("STOP",       pc -> speakerStop());
            handlers.put("MUTE",   pc -> speakerMute());
            handlers.put("UNMUTE", pc ->  speakerUnmute()); 
        }
        void speakerPlay(String url, String track){ /* ... */ }
        void speakerStop(){ /* ... */ }
        void speakerMute(){ /* ... */ }
        void speakerUnmute(){ /* ... */ }
    }
}
