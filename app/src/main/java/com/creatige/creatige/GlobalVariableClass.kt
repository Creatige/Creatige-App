package com.creatige.creatige

class GlobalVariableClass {

    companion object {

        // This is our anonymous API Key: QcRxfonkWODntMJO7sOiNA
        val api_key = "QcRxfonkWODntMJO7sOiNA"

        val api_generate_url = "https://stablehorde.net/api/v2/generate/async"
//        val api_status_url = "https://stablehorde.net/api/v2/generate/status/"
        val api_retrieve_img_url = "https://stablehorde.net/api/v2/generate/status/"


        /* Generation parameters not exposed to the user */
        // Whether to use only trusted Horde workers. Setting it to true is safer (in case there are troll workers) but it also makes generation potentially slower as we can't get generations from new workers
        // TODO: Set all the following 3 to true for demo day
        val trusted_workers = false
        // Whether to censor accidental nsfw images that we could potentially get from the worker
        val censor_nsfw = true
        // Whether our prompt has NSFW content; some workers disable NSFW so they don't get any of those requests
        val nsfw_enabled = false


        // Default values for the generation parameters values

        val def_steps = 15
        val def_width = 8 // needs to be multiplied by 64 to get the real value
        val def_height = 8 // needs to be multiplied by 64 to get the real value
        val def_guidance = 14 // needs to be divided by 2 to get the real value
        val def_denoising = 14 // needs to be divided by 20 to get the real value

        // Number of seconds to wait before retrieving image in Create
        val wait_in_create_seconds = 30;

    }
}